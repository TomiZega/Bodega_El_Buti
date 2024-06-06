package com.diseno.ppai.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.diseno.ppai.repository.ProvinciaRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class RegionVitivinicola {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idRegion;

    private String descripcion;
    private String nombre;

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Transient
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Provincia obtProvincia() {
        List<Provincia> provincias = provinciaRepository.findAll();
        for (Provincia provincia : provincias) {
            if (provincia.esDeRegion(this)) {
                return provincia;
            }
        }
        return null;
    }

    
}