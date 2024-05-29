package com.diseno.ppai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Varietal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idVarietal;

    private String descripcion;

    public Integer getIdVarietal() {
        return idVarietal;
    }

    public void setIdVarietal(Integer idVarietal) {
        this.idVarietal = idVarietal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
