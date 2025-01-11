package ThirdExample;

import org.springframework.stereotype.Component;

//@Component
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
