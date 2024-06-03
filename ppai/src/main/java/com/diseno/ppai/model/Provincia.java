package com.diseno.ppai.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.util.List;

import com.diseno.ppai.repository.PaisRepository;

@Entity
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProvincia;

    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provincia_id")
    private List<RegionVitivinicola> regiones;

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<RegionVitivinicola> getRegiones() {
        return regiones;
    }

    public void setRegiones(List<RegionVitivinicola> regiones) {
        this.regiones = regiones;
    }

    public boolean esDeRegion(RegionVitivinicola region){
        return regiones.contains(region);
    }

    @Transient PaisRepository paisRepository;
    public Pais obtPais(){
        List<Pais> paises = paisRepository.findAll();
        for (Pais pais : paises) {
            if (pais.esDeProvincia(this)) {
                return pais;
            }
        }
        return null;
    }

}
