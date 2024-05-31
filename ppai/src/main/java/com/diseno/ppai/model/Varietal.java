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

    private Integer porcentajeComposicion;

    public Integer getPorcentajeComposicion() {
        return porcentajeComposicion;
    }

    public void setPorcentajeComposicion(Integer porcentajeComposicion) {
        this.porcentajeComposicion = porcentajeComposicion;
    }

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
