package com.diseno.ppai.repository;

public class PaisRepositoryHolder {

    private static PaisRepository paisRepository;

    public static void setPaisRepository(PaisRepository repository) {
        paisRepository = repository;
    }

    public static PaisRepository getPaisRepository() {
        return paisRepository;
    }
}
