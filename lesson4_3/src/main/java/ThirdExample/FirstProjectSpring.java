//Конфигурация с помощью java code
package ThirdExample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstProjectSpring {
    public static void main(String[] args)  {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationClass.class);

//        Person person = applicationContext.getBean("person", Person.class);
//        person.voiceMyMachine();
//        person.infoMyMachine();

        Car car = applicationContext.getBean("car", Car.class);
        car.voice();
        car.info();
//        Moto moto = applicationContext.getBean("moto", Moto.class);
//        moto.voice();
//        moto.info();

        applicationContext.close();

    }
}
