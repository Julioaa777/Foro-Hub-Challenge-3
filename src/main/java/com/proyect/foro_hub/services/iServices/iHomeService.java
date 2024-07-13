package com.proyect.foro_hub.services.iServices;
import com.proyect.foro_hub.models.dtos.tema.Genero;
import com.proyect.foro_hub.models.dtos.tema.TemaDto;

import java.time.LocalDate;
import java.util.List;

public interface iHomeService {

    List<TemaDto> findByGenero(Genero genero);
    List<TemaDto> getTemasByDate(LocalDate localDate);


}
