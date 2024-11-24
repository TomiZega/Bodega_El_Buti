package com.diseno.ppai;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.diseno.ppai.repository.PaisRepository;
import com.diseno.ppai.repository.PaisRepositoryHolder;

@SpringBootApplication
public class PpaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpaiApplication.class, args);
    }
}
