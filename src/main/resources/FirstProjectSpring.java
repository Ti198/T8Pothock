package Introduction_Spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstProjectSpring {
    public static void main(String[] args) throws InterruptedException {
//        while (true) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//            Machine machine = applicationContext.getBean("myMachine", Machine.class);
//            machine.voice();

        Person person = applicationContext.getBean("person", Person.class);
        person.voiceMyMachine();
        person.infoMyMachine();

//            Thread.sleep(5000);
//        }
    }
}

