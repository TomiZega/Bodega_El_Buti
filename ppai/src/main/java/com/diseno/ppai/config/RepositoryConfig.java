package com.diseno.ppai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.diseno.ppai.repository.ProvinciaRepository;
import com.diseno.ppai.repository.ProvinciaRepositoryHolder;

import jakarta.annotation.PostConstruct;

@Configuration
public class RepositoryConfig {
    
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @PostConstruct
    public void init() {
        ProvinciaRepositoryHolder.setProvinciaRepository(provinciaRepository);
    }
}
