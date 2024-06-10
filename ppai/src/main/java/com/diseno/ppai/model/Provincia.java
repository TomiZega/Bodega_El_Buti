package com.diseno.ppai.model;

import java.util.List;
import java.util.Objects;

import com.diseno.ppai.repository.PaisRepositoryHolder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

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
        for (RegionVitivinicola regionVitivinicola : regiones) {
            if (Objects.equals(region.getIdRegion(),regionVitivinicola.getIdRegion())){
                return true;
            }
        }
        return false;
    }

    public Pais obtPais( ){
        List<Pais> paises = PaisRepositoryHolder.getPaisRepository().findAll();
        for (Pais pais : paises) {
            if (pais.esDeProvincia(this)) {
                return pais;
            }
        }
        return null;
    }

}
