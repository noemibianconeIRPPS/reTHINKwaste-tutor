package it.cnr.rethinkwaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RethinkwasteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RethinkwasteApplication.class, args);
    }

}
