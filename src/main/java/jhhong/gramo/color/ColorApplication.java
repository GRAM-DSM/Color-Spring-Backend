package jhhong.gramo.color;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ColorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColorApplication.class, args);
    }

}
