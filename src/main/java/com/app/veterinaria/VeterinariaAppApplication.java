package com.app.veterinaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class VeterinariaAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeterinariaAppApplication.class, args);
    }

}
