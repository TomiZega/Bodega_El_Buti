package com.diseno.ppai.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.diseno.ppai.model.Vino;
import com.diseno.ppai.repository.VinoRepository;
import com.diseno.ppai.strategy.EstrategiaResenasAmigos;
import com.diseno.ppai.strategy.EstrategiaResenasNormales;
import com.diseno.ppai.strategy.EstrategiaResenasSommelier;
import com.diseno.ppai.strategy.IEstrategia;

import jakarta.annotation.PostConstruct;

@RestController
public class GestorGenerarReporteRankingVinos {
    private Date fechaInicio;
    private Date fechaFin;
    private static final Map<String, Class<? extends IEstrategia>> estrategias = Map.of(
        "Reseñas normales", EstrategiaResenasNormales.class,
        "Reseñas de Sommelier", EstrategiaResenasSommelier.class,
        "Reseñas de Amigos", EstrategiaResenasAmigos.class
        );
        private Class<? extends IEstrategia> estrategiaSelecClass;
    private String tipoResenaSelec;
    private String tipoVisualizacionSelec;
    private IEstrategia estrategiaSelec;
    private List<Vino> vinos;

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
        Class<? extends IEstrategia> estrategiaClass = estrategias.get(tipoResena);
        this.estrategiaSelecClass = estrategiaClass;
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
    
        // Check if report generation was successful
        if (reportBytes.length == 0) {
            return ResponseEntity.noContent().build();
        }
    
        // Ensure valid visualization and selection type
        if ("Excel".equals(this.tipoVisualizacionSelec) && this.tipoResenaSelec != null) {
            try {
                InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(reportBytes));
    
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=RankingVinos.xlsx");
    
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .body(resource);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    

    // 18
    public byte[] calificarVinos() {
        this.estrategiaSelec= crearEstrategia();
        List<Map<String,String>> listaDatos = estrategiaSelec.calificarVinos(vinos, fechaInicio, fechaFin);
        ordenarVinosSegunCalificacion(listaDatos);
        return generarReporte(listaDatos);
    }

    public void ordenarVinosSegunCalificacion(List<Map<String, String>> datos) {
        Collections.sort(datos, (map1, map2) -> {
            String puntaje1Str = map1.get("Puntaje Promedio");
            String puntaje2Str = map2.get("Puntaje Promedio");
    
            // Default to 0.0 if null or empty
            Double calificacion1 = (puntaje1Str != null && !puntaje1Str.isBlank())
                    ? Double.parseDouble(puntaje1Str)
                    : 0.0;
            Double calificacion2 = (puntaje2Str != null && !puntaje2Str.isBlank())
                    ? Double.parseDouble(puntaje2Str)
                    : 0.0;
    
            return calificacion2.compareTo(calificacion1); // Descending order
        });
    }
    
    public byte[] generarReporte(List<Map<String, String>> datos) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
    
            Sheet sheet = workbook.createSheet("Ranking Vinos");
    
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Nombre", "Precio", "Puntaje Promedio", "Porcentaje Composición", "Región", "Provincia", "País" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
    
            // Fill data rows
            int rowNum = 1;
            for (Map<String, String> vinoData : datos) {
                if (rowNum > 11){break;} // Stop after 10 rows
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < headers.length; i++) {
                    String value = vinoData.get(headers[i]);
                    row.createCell(i).setCellValue(value != null ? value : ""); // Handle potential null values
                }
            }
    
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
    
    

    public IEstrategia crearEstrategia() {
        try {
            return estrategiaSelecClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la estrategia seleccionada", e);
        }
    }
    
    

    public void finCU() {
    }
}
