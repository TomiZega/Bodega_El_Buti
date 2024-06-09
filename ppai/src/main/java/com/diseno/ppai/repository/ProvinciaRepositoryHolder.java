package com.diseno.ppai.repository;

public class ProvinciaRepositoryHolder {

    private static ProvinciaRepository provinciaRepository;

    public static void setProvinciaRepository(ProvinciaRepository repository) {
        provinciaRepository = repository;
    }

    public static ProvinciaRepository getProvinciaRepository() {
        return provinciaRepository;
    }
}
