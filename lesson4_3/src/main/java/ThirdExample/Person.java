package ThirdExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class Person {




//    @Qualifier("car")
//    @Autowired
    private Machine machine;

    public Person(Car car) {
    }
//    public Person(Machine machine) {
//        this.machine = machine;
//    }
//    public Person() {}

    public Machine getMachine() {
        return machine;
    }

//    public void setMachine(Machine machine) {
//        this.machine = machine;
//    }

    public void voiceMyMachine(){
        machine.voice();
    }
    public void infoMyMachine() {
        machine.info();
    }
}
