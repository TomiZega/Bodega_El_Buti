package com.diseno.ppai.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.diseno.ppai.model.Resena;
import com.diseno.ppai.model.Vino;
import com.diseno.ppai.repository.VinoRepository;

import jakarta.annotation.PostConstruct;

@RestController
public class GestorGenerarReporteRankingVinos {
    private Date fechaInicio;
    private Date fechaFin;
    private List<String> tiposResenas = Arrays.asList("Reseñas normales", "Reseñas de Sommelier", "Reseñas de Amigos");
    private String tipoResenaSelec;
    private List<String> tipoVisualizacion = Arrays.asList("Excel", "PDF", "En pantalla");
    private String tipoVisualizacionSelec;
    private List<Vino> vinos;
    private List<Vino> vinosConResenasValidas;
    private List<Resena> resenasValidas;

    @Autowired
    private VinoRepository vinoRepository;

    @PostConstruct
    public void init() {
        this.vinos = vinoRepository.findAll();
    }

    // 5
    @PostMapping(path = "fechas-rango")
    public void tomarFechasDesdeHasta(
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaHasta) {
        this.fechaInicio = fechaDesde;
        this.fechaFin = fechaHasta;
        validarPeriodoCorrecto(fechaDesde, fechaHasta);
    }

    // 10
    @PostMapping("tipo-resena")
    public void tomarTipoResena(@RequestBody String tipoResena) {
        this.tipoResenaSelec = tipoResena;
    }

    // 13
    @PostMapping("tipo-visualizacion")
    public void tomarTipoVisualizacion(@RequestBody String tipoVisualizacion) {
        this.tipoVisualizacionSelec = tipoVisualizacion;
    }

    // 2
    public void generarRankingVinos() {
    }

    // 7
    public boolean validarPeriodoCorrecto(Date fecha1, Date fecha2) {
        return fecha1.getTime() < fecha2.getTime();
    }

    @GetMapping("/calificar-vinos")
    public ResponseEntity<InputStreamResource> tomarConfirmacion() {
        byte[] reportBytes = calificarVinos();

        // Send the generated file as a response
        try {
            // Convert report content to InputStreamResource
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(reportBytes));

            // Set up response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=RankingVinos.xlsx");

            // Return the file as a response
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType
                            .parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 19
    public byte[] calificarVinos() {
        vinos.forEach(vino -> {
            if (vino.tieneResena()) {
                vino.getResenas().forEach(resena -> {
                    if (resena.esFechaValida(fechaInicio, fechaFin) && resena.sosDeSommelier()) {
                        resenasValidas.add(resena);
                        if (!vinosConResenasValidas.contains(vino)) {
                            vinosConResenasValidas.add(vino);
                        }
                    }
                });
            }
        });
        vinosConResenasValidas.forEach(vino -> {
            List<Resena> resenasValidasDelVino = new ArrayList<Resena>();
            vino.getResenas().forEach(resena -> {
                if (resenasValidas.contains(resena)) {
                    resenasValidasDelVino.add(resena);
                }
            });
            Float puntaje = vino.getPuntaje(resenasValidasDelVino);
            vino.setPromedioPuntaje(puntaje);
        });
        //
        ordenarVinosSegunCalificacion();
        return generarReporte();

    }

    public void ordenarVinosSegunCalificacion() {
        Collections.sort(vinosConResenasValidas, Comparator.comparing(Vino::getPromedioPuntaje).reversed());
    }

    public byte[] generarReporte() {
        try (Workbook workbook = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream("RankingVinos.xlsx")) {

            Sheet sheet = workbook.createSheet("Ranking Vinos");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Nombre", "Precio", "Calificación", "Porcentaje Composición", "Región", "Provincia",
                    "País" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Vino vino : vinosConResenasValidas) {
                Row row = sheet.createRow(rowNum++);
                String[] datosVino = vino.mostrarDatosDelVino().split(",");
                String[] ubicacionVino = vino.mostrarUbicacionVino().split(",");

                for (int i = 0; i < datosVino.length; i++) {
                    row.createCell(i).setCellValue(datosVino[i]);
                }
                for (int i = 0; i < ubicacionVino.length; i++) {
                    row.createCell(datosVino.length + i).setCellValue(ubicacionVino[i]);
                }
            }

            // Adjust column width
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to the file
            workbook.write(fileOut);
            return new byte[0]; // Or return some indicator of success if needed
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public void finCU() {
    }
}
