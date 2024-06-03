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
import java.util.Random;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import com.diseno.ppai.model.Resena;
import com.diseno.ppai.model.Vino;
import com.diseno.ppai.repository.VinoRepository;

import jakarta.annotation.PostConstruct;

@RestController
public class GestorGenerarReporteRankingVinos {
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoResenaSelec;
    private String tipoVisualizacionSelec;
    private List<Vino> vinos;
    private List<Vino> vinosConResenasValidas = new ArrayList<Vino>();
    private List<Resena> resenasValidas = new ArrayList<Resena>();

    @Autowired
    private VinoRepository vinoRepository;

    @PostConstruct
    public void init() {
        this.vinos = vinoRepository.findAll();
    }
    //5
    @PostMapping(path = "fechas-rango")
    public ResponseEntity<String> tomarFechasDesdeHasta(
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        try {
            this.fechaInicio = fechaDesde;
            this.fechaFin = fechaHasta;
            if(validarPeriodoCorrecto(fechaDesde, fechaHasta)){
                return ResponseEntity.ok("Fecha valida");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("periodo incorrecto");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fecha inválida");
        }
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

    @GetMapping("generar-ranking-vinos")
    public ResponseEntity<String> generarRankingVinos() {
        try {    
            // Return success response
            return ResponseEntity.ok("Ranking generated successfully");
        } catch (Exception e) {
            // Log the error and return a failure response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate ranking");
        }
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
            System.out.println("Vino: "+vino.toString());
            if (vino.tieneResena()) {
                vino.getResenas().forEach(resena -> {
                    System.out.println("Resena: "+resena.toString());
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
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        Sheet sheet = workbook.createSheet("Ranking Vinos");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Nombre", "Precio", "Calificación", "Porcentaje Composición", "Región", "Provincia",
                "País" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows with random values
        int rowNum = 1;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Row row = sheet.createRow(rowNum++);
            // Generate random values for each column
            row.createCell(0).setCellValue("Vino " + rowNum); // Nombre
            row.createCell(1).setCellValue(Math.random() * 500); // Precio
            row.createCell(2).setCellValue((random.nextInt(10) + 1)); // Calificacion
            row.createCell(3).setCellValue(Math.random() * 100); // Porcentaje Composicion
            row.createCell(4).setCellValue("Region " + (int) (Math.random() * 5)); // Region
            row.createCell(5).setCellValue("Provincia " + (int) (Math.random() * 3)); // Provincia
            row.createCell(6).setCellValue("Pais " + (int) (Math.random() * 2)); // Pais
        }
    // Determine the range of cells to apply the auto-filter
    CellRangeAddress range = new CellRangeAddress(0, rowNum - 1, 0, 7 - 1);

    // Apply the auto-filter to the specified range
    sheet.setAutoFilter(range);

        // Adjust column width
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook data to ByteArrayOutputStream
        workbook.write(out);
        return out.toByteArray();
    } catch (IOException e) {
        e.printStackTrace();
        return new byte[0];
    }
}
    
    

    public void finCU() {
    }
}
