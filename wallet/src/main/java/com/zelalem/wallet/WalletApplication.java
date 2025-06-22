package com.zelalem.wallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Wallet Management",
                version = "1.0",
                contact = @Contact(
                        name = "The Developer",
                        url = "mailto:zelalemgizachew890@gmail.com",
                        email = "zelalemgizachew890@gmail.com"
                ),
                description = "Wallet Management Micro-Service for Financial Application.")
)
public class WalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }

}
