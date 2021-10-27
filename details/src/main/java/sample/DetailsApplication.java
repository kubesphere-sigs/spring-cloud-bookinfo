package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DetailsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DetailsApplication.class, args);
    }
}