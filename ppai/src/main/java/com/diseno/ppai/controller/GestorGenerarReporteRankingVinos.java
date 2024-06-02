package com.diseno.ppai.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diseno.ppai.model.Vino;
import com.diseno.ppai.repository.VinoRepository;

import jakarta.annotation.PostConstruct;

@RestController
public class GestorGenerarReporteRankingVinos {
    private Date fechaInicio;
    private Date fechaFin;
    private List<String> tiposResenas = Arrays.asList("Reseñas normales","Reseñas de Sommelier", "Reseñas de Amigos");
    private String tipoResenaSelec;
    private List<String> tipoVisualizacion = Arrays.asList("Excel","PDF", "En pantalla");
    private String tipoVisualizacionSelec;
    private List<Vino> vinos;

    @Autowired
    private VinoRepository vinoRepository;
    @PostConstruct
    public void init() {
        vinos = vinoRepository.findAll();
    }


    //5
    @PostMapping(path = "fechas-rango")
    public void tomarFechasDesdeHasta(
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaHasta) {
        this.fechaInicio = fechaDesde;
        this.fechaFin = fechaHasta;
        validarPeriodoCorrecto(fechaDesde, fechaHasta);
    };


    //10
    @PostMapping("tipo-resena")
    public void tomarTipoResena(@RequestBody String tipoResena) {
        this.tipoResenaSelec = tipoResena;
    };

    //13
    @PostMapping("tipo-visualizacion")
    public void tomarTipoVisualizacion(@RequestBody String tipovisualizacion) {
        this.tipoVisualizacionSelec= tipovisualizacion;
    };
    
    //2
    public void generarRankingVinos() {
    };
    //7
    public boolean validarPeriodoCorrecto(Date fecha1, Date fecha2) {
        return fecha1.getTime() < fecha2.getTime();
    };

    //16
    public void tomarConfirmacion(){};


    //17
    public void validarConfirmacion(){};
    //18
    public Date getFechaActual() {
        return new Date();
    };

    //19
    public void calificarVinos(){};

    public void ordenarVinosSegunCalificacion() {
        vinos.forEach(vino -> {
            if (vino.tieneResena()) {
                // Process the wine if it has reviews
                System.out.println(vino.getNombre() + " has reviews.");
            }
        });
    };

    public void generarReporte() {
    };

    public void finCU() {
    };

}
