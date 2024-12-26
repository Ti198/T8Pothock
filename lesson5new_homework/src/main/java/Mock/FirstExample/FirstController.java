//Разработать заглушку, обрабатывающую следующие запросы со стороны псевдотестируемой системы.
//1. Get localhost:8080/app/v1/getRequest?id={id}&name={name}, где id>10 и длина name >5.
//В случае, если какое условие не выполняется, вернуть InternalServerError и ннапечатать причину ошибки.
//Вернуть тело ответа из текстового файла getAnswer.txt и подставить в него поле name.
//2. Post localhost:8080/app/v1/postRequest
//body:{"name":"{name}","surname":"{surname}","age":{age}}
//где {name}, {surname}, {age} должны быть непустыми, в противном случае вернуть InternalServerError
//Вернуть ответ из файла postAnswer.txt, подставив в него данные из тела запроса.
//3. Для GET запроса реализовать в случае id>10 and 1d<50 время задержки =1000мс, во всех остальных случаях =500мс.
package Mock.FirstExample;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController

public class FirstController {

    String body;
    String postBody;

    @PostConstruct
    public void init() throws IOException {
        Path path = Path.of("getAnswer.txt");
        Path path1 = Path.of("postAnswer.txt");
        body = Files.readString(path);
        postBody = Files.readString(path1);
    }

    @GetMapping(value = "/app/v1/getRequest")
    public ResponseEntity getStatus(@RequestParam(value = "id") Integer id,
                                    @RequestParam(value = "name") String name
    ) throws InterruptedException {

        if (id > 10 && id < 50) {
            Thread.sleep(1000);
        } else {
            Thread.sleep(500);
        }
        if (id <= 10 && name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid id & Invalid name");
        } else if (id <= 10) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid id");
        } else if (name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid name");
        }

        body = String.format(body, id, name);
        return ResponseEntity.ok(body);
    }

    @PostMapping(value = "/app/v1/postRequest")
    public ResponseEntity addTask(@RequestBody Person person) {
        System.out.println(person);
        String name = person.getName();
        String surname = person.getSurname();
        Integer age = person.getAge();
        if ((name == "") || (surname == "") || (age == null)) {
            ResponseEntity valueIsNull = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Value is null");
            return valueIsNull;
        }
        postBody = String.format(postBody, name, surname, age, name, surname, age * 2);
        return ResponseEntity.ok(postBody);

    }
}

