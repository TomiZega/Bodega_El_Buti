package com.diseno.ppai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idBodega;

    private String descripcion;
    private String historia;
    private String nombre;
    private Integer coordenadasUbicacion;
    private String periodoActualizacion;
    
    @ManyToOne
    private RegionVitivinicola region;

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getHistoria() {
        return historia;
    }
    public void setHistoria(String historia) {
        this.historia = historia;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCoordenadasUbicacion() {
        return coordenadasUbicacion;
    }
    public void setCoordenadasUbicacion(Integer coordenadasUbicacion) {
        this.coordenadasUbicacion = coordenadasUbicacion;
    }
    public String getPeriodoActualizacion() {
        return periodoActualizacion;
    }
    public void setPeriodoActualizacion(String periodoActualizacion) {
        this.periodoActualizacion = periodoActualizacion;
    }
    public RegionVitivinicola getRegion() {
        return region;
    }
    public void setRegion(RegionVitivinicola region) {
        this.region = region;
    }

    
}
