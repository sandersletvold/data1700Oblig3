package oslomet.data1700.oblig3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Bruk (exclude={SecurityAutoConfiguration.class}) hvis du har implementert spring security
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class Oblig3Application {

    public static void main(String[] args) {
        SpringApplication.run(Oblig3Application.class, args);
    }

}
