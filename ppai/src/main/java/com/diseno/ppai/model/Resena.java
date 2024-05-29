package com.diseno.ppai.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Resena {

    @Id
    @GeneratedValue()
    private Integer idResena;

    @ManyToOne
    @JoinColumn(name="idVino", nullable=false)
    private Vino vino;
    private String comentario;
    private Boolean esPremium;
    private Date fechaResenia;
    private Integer puntaje;

    public Vino getVino() {
        return vino;
    }
    public void setVino(Vino vino) {
        this.vino = vino;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public Boolean getEsPremium() {
        return esPremium;
    }
    public void setEsPremium(Boolean esPremium) {
        this.esPremium = esPremium;
    }
    public Date getFechaResenia() {
        return fechaResenia;
    }
    public void setFechaResenia(Date fechaResenia) {
        this.fechaResenia = fechaResenia;
    }
    public Integer getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
    public Integer getIdResena() {
        return idResena;
    }
    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }
   
}
