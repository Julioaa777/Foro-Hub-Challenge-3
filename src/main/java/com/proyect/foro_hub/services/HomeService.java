package com.proyect.foro_hub.services;
import com.proyect.foro_hub.models.dtos.tema.Genero;
import com.proyect.foro_hub.models.dtos.tema.TemaDto;
import com.proyect.foro_hub.services.iServices.iHomeService;

import java.time.LocalDate;
import java.util.List;

public class HomeService implements iHomeService {

    @Override
    public List<TemaDto> findByGenero(Genero genero) {
        return null;
    }

    @Override
    public List<TemaDto> getTemasByDate(LocalDate localDate) {
        return null;
    }
}
