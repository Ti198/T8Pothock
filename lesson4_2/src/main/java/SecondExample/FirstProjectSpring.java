package SecondExample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstProjectSpring {
    public static void main(String[] args)  {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Person person = applicationContext.getBean("person", Person.class);
        person.voiceMyMachine();
        person.infoMyMachine();

//        Car car = applicationContext.getBean("car", Car.class);
//        car.voice();
//        car.info();
//        Moto moto = applicationContext.getBean("moto", Moto.class);
//        moto.voice();
//        moto.info();

        applicationContext.close();

    }
}
