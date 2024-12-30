//Задача парсинга лог-файла
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {

    private static final String LOG_PATTERN = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\+\\d{2}:\\d{2})~(GET|POST) \"(.*?)$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static void main(String[] args) {
        //****Создаем новый файл только с api, обрезаем токены и данные, передаваемые get-запросами
        String inputFilePath = "production_log.csv";  // Путь к исходному файлу
        String outputFilePath = "input.txt"; // Путь к выходному файлу

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Ищем индекс последовательности "/api/"
                int apiIndex = line.indexOf("/api/");
                if (apiIndex != -1) {
                    // Находим индекс следующего слеша, кавычки или вопросительного знака
                    int endIndex = line.length(); // Изначально предполагаем, что это конец строки
                    int slashIndex = line.indexOf('/', apiIndex + 5);
                    int quoteIndex = line.indexOf('"', apiIndex + 5);
                    int questionMarkIndex = line.indexOf('?', apiIndex + 5);

                    // Если слеш найден, обновляем endIndex
                    if (slashIndex != -1) {
                        endIndex = slashIndex;
                    }
                    // Если кавычка найдена и меньше endIndex, обновляем endIndex
                    if (quoteIndex != -1 && quoteIndex < endIndex) {
                        endIndex = quoteIndex;
                    }
                    // Если вопросительный знак найден и меньше endIndex, обновляем endIndex
                    if (questionMarkIndex != -1 && questionMarkIndex < endIndex) {
                        endIndex = questionMarkIndex;
                    }

                    // Записываем строку от начала до найденного endIndex
                    writer.write(line.substring(0, endIndex + 1));
                    writer.newLine(); // Переход на новую строку
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //***Решаем задачу парсинга

        String logFilePath = "input.txt"; // путь к новому лог-файлу

        //Структура данных: используется Map<String, Map<String, Integer>>,
        // где ключом первого уровня является метод, а ключом второго уровня — минутный интервал.
        // Значением второго уровня является количество запросов за эту минуту.
        Map<String, Map<String, Integer>> methodCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line, methodCounts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Вывод пиковых нагрузок
        for (String method : methodCounts.keySet()) {
            Map<String, Integer> counts = methodCounts.get(method);
            String peakMinute = counts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
            Integer peakCount = counts.get(peakMinute);
            System.out.println("Method: " + method + ", Peak Minute: " + peakMinute + ", RPS: " + peakCount);
        }
    }

    private static void parseLine(String line, Map<String, Map<String, Integer>> methodCounts) {
        Pattern pattern = Pattern.compile(LOG_PATTERN);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String timestamp = matcher.group(1);
            String method = matcher.group(3);

            // Проверяем, начинается ли метод с /api/
            if (method.startsWith("/api/")) {
                LocalDateTime dateTime = LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER);
                String minuteKey = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                methodCounts.putIfAbsent(method, new HashMap<>());
                methodCounts.get(method).put(minuteKey, methodCounts.get(method).getOrDefault(minuteKey, 0) + 1);
            }
        }
    }
}


