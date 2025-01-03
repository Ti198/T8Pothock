package Introduction_Spring;

public class Person {
    private Machine machine;
    public Person(Machine machine) {
        this.machine = machine;
    }
    public Person() {}

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void voiceMyMachine(){
        machine.voice();
    }
    public void infoMyMachine() {
        machine.info();
    }
}







