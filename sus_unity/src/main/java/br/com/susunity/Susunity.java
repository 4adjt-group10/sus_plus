package br.com.susunity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Susunity {
    public static void main(String[] args) {
        SpringApplication.run(Susunity.class, args);
    }
}