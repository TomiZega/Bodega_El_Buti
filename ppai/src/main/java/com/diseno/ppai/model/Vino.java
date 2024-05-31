package com.diseno.ppai.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Vino {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idVino;

    private String nombre;
    private Date aniada;
    private Date fechaActualizacion;
    private String imagen;
    private Float precio;
    private Float notaDeCataDeBodega;

    @ManyToOne
    private Bodega bodega;

    @ManyToOne
    private Varietal varietal;

    @OneToMany(mappedBy = "vino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getAniada() {
        return aniada;
    }
    public void setAniada(Date aniada) {
        this.aniada = aniada;
    }
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public Float getPrecio() {
        return precio;
    }
    public void setPrecio(Float precio) {
        this.precio = precio;
    }
    public Float getNotaDeCataDeBodega() {
        return notaDeCataDeBodega;
    }
    public void setNotaDeCataDeBodega(Float notaDeCataDeBodega) {
        this.notaDeCataDeBodega = notaDeCataDeBodega;
    }
    public Bodega getBodega() {
        return bodega;
    }
    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }
    public Integer getIdVino() {
        return idVino;
    }
    public void setIdVino(Integer idVino) {
        this.idVino = idVino;
    }
    public List<Resena> getResenas() {
        return resenas;
    }
    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }
    
}
