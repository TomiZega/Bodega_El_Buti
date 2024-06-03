package com.diseno.ppai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diseno.ppai.model.Provincia;
import com.diseno.ppai.model.RegionVitivinicola;
import com.diseno.ppai.repository.ProvinciaRepository;

@Service
public class RegionService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Provincia findProvinciaByRegion(RegionVitivinicola region) {
        List<Provincia> provincias = provinciaRepository.findAll();
        for (Provincia provincia : provincias) {
            if (provincia.esDeRegion(region)) {
                return provincia;
            }
        }
        return null;
    }
}
