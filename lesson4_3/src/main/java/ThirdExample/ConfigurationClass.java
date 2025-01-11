package ThirdExample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("ThirdExample")
@Configuration
public class ConfigurationClass {

    @Bean
    public Car car(){
        return new Car();
    }
    @Bean
    public Moto moto(){
        return new Moto();
    }

    @Bean
    public Ship ship(){
        return new Ship();
    }

    @Bean
    public Person person(){
        return new Person(car());
    }

}
