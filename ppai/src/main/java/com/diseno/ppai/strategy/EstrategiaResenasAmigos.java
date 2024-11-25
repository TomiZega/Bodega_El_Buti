package com.diseno.ppai.strategy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.diseno.ppai.model.Resena;
import com.diseno.ppai.model.Vino;

public class EstrategiaResenasAmigos  implements IEstrategia{
    @Override
    public List<Map<String,String>> calificarVinos(List<Vino> vinos, Date fechaInicio, Date fechaFin) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Resena> obtResenasValidas(Vino vino, Date fechaInicio, Date fechaFin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtResenasValidas'");
    }

    @Override
    public String obtPuntajeVino(Vino vino, List<Resena> resenas) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtPuntajeVino'");
    }
}
