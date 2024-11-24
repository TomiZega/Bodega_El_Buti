package com.diseno.ppai.strategy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.diseno.ppai.model.Vino;

public interface IEstrategia {
    // Recibe todos los punteros de los vinos y devuelve un array de string
    // Definir bien el tipo de dato de retorno
    public List<Map<String, String>> calificarVinos(List<Vino> vinos, Date fechaInicio, Date fechaFin);
}
