package com.diseno.ppai.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.diseno.ppai.model.Resena;
import com.diseno.ppai.model.Vino;

public class EstrategiaResenasSommelier implements IEstrategia {

    public List<Resena> obtResenasValidas(Vino vino, Date fechaInicio, Date fechaFin) {
        List<Resena> resenasValidas = new ArrayList<Resena>();
        for (Resena resena : vino.getResenas()) {
            if (resena.esFechaValida(fechaInicio, fechaFin) && resena.sosDeSommelier()) {
                resenasValidas.add(resena);
            }
        }
        return resenasValidas;
    }

    public String obtPuntajeVino(Vino vino, List<Resena> resenas) {
        return vino.mostrarPuntajeAcumulado(resenas).toString();
    }

    @Override
    public List<Map<String, String>> calificarVinos(List<Vino> vinos, Date fechaInicio, Date fechaFin) {
        List<Vino> vinosConResenasValidas = new ArrayList<>();
        List<Resena> resenasValidas = new ArrayList<>();

        // Obtener todos los vinos con reseñas válidas
        vinos.forEach(vino -> {
            if (vino.tieneResena()) {
                resenasValidas.addAll(obtResenasValidas(vino, fechaInicio, fechaFin));
                // Solo incluir vinos con reseñas válidas
                if (!resenasValidas.isEmpty()) {
                    vinosConResenasValidas.add(vino);
                }
            }
        });

        // Lista de mapas de propiedades del vino
        List<Map<String, String>> resultado = new ArrayList<>();

        // Procesar cada vino con reseñas que cumplen el filtro
        vinosConResenasValidas.forEach(vino -> {
            // Obtener solo las reseñas válidas del vino
            List<Resena> resenasValidasDelVino = new ArrayList<>();
            vino.getResenas().forEach(resena -> {
                if (resenasValidas.contains(resena)) {
                    resenasValidasDelVino.add(resena);
                }
            });

            // Crear el mapa de propiedades para el vino actual
            Map<String, String> mapaVino = new HashMap<>();

            // Usar métodos existentes para obtener datos y ubicación
            String puntaje = obtPuntajeVino(vino, resenasValidasDelVino);
            String[] datosDelVino = vino.mostrarDatosDelVino().split(",");
            String[] ubicacionDelVino = vino.mostrarUbicacionVino().split(",");

            // Mapear los datos del vino
            mapaVino.put("Nombre", datosDelVino[0]);
            mapaVino.put("Precio", datosDelVino[1]);
            mapaVino.put("Puntaje Promedio", puntaje);
            mapaVino.put("Porcentaje Composición", datosDelVino[3] + "%");

            // Mapear la ubicación del vino
            mapaVino.put("Región", ubicacionDelVino[0]);
            mapaVino.put("Provincia", ubicacionDelVino[1]);
            mapaVino.put("País", ubicacionDelVino[2]);

            // Agregar el mapa a la lista de resultados
            resultado.add(mapaVino);
        });

        return resultado;
    }

}
