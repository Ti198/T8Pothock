package Introduction_Spring;

public class Moto implements Machine{
    @Override
    public void voice(){
        System.out.println("Moto's voice");
    }
    @Override
    public void info(){
        System.out.println("no information");
    };
}