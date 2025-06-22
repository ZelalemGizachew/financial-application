package com.zelalem.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "User Management",
                version = "1.0",
                contact = @Contact(
                        name = "The Developer",
                        url = "mailto:zelalemgizachew890@gmail.com",
                        email = "zelalemgizachew890@gmail.com"
                ),
                description = "User Management Micro-Service for Financial Application.")
)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
