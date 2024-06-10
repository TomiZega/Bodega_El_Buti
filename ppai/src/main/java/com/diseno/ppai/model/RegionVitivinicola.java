package com.diseno.ppai.model;

import java.util.List;
import java.util.Objects;

import com.diseno.ppai.repository.ProvinciaRepositoryHolder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    public Integer getIdRegion() {
        return idRegion;
    }
    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Provincia obtProvincia() {
        List<Provincia> provincias = ProvinciaRepositoryHolder.getProvinciaRepository().findAll();
        for (Provincia provincia : provincias) {
            if (provincia.esDeRegion(this)) {
                return provincia;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionVitivinicola that = (RegionVitivinicola) o;
        return Objects.equals(idRegion, that.idRegion) &&
               Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegion, nombre);
    }
    
}