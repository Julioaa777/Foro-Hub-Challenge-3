package com.proyect.foro_hub.services;
import com.proyect.foro_hub.exceptions.BadRequestExcepton;
import com.proyect.foro_hub.exceptions.ResourceNotFoundException;
import com.proyect.foro_hub.models.Respuesta;
import com.proyect.foro_hub.models.Tema;
import com.proyect.foro_hub.models.Usuario;
import com.proyect.foro_hub.models.dtos.respuesta.RespuestaTemaDTO;
import com.proyect.foro_hub.models.dtos.tema.TemaActualizarDTO;
import com.proyect.foro_hub.models.dtos.tema.TemaDto;
import com.proyect.foro_hub.repository.iTemaRepository;
import com.proyect.foro_hub.repository.iUsuarioRepository;
import com.proyect.foro_hub.services.iServices.iTemaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemaService implements iTemaService {

    @Autowired
    private iTemaRepository temaRepository;

    @Autowired
    private iUsuarioRepository usuarioRepository;

    @Override
    public List<TemaDto> findAll() {
        List<Tema>temas = temaRepository.findAll();
        return temas.stream()
                .map(tema -> manejoRespuestaCliente(tema, true))// Transforma cada Tema a TemaDto reducido
                .collect(Collectors.toList());
    }

    @Override
    public Page<TemaDto> paginate(Pageable pageable) {
        Page<Tema> temas = temaRepository.findAll(pageable);
        return temas.map(tema -> manejoRespuestaCliente(tema, true));
    }

    public TemaDto findById(Integer id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));
        return manejoRespuestaCliente(tema, true);
    }

    public TemaDto save(TemaDto temaDto) {
        boolean tituloExiste = temaRepository.existsByTitulo(temaDto.getTitulo());
        boolean mensajeExiste = temaRepository.existsByMensaje(temaDto.getMensaje());

        if (tituloExiste ) {
            throw new BadRequestExcepton("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestExcepton("El mensaje ya exisite!");
        }

        Usuario usuario = usuarioRepository.findById(temaDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));


        Tema tema = new ModelMapper().map(temaDto, Tema.class);
        try {
            tema.setCreatedAt(LocalDateTime.now());
            tema.setUsuarioId(usuario);
            tema.setActivo(Boolean.TRUE);
            tema = temaRepository.save(tema);
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR CREACION TEMA: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaCliente(tema, false);
    }

    public TemaDto update(Integer id, TemaActualizarDTO temaActualizarDTO) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));

        boolean tituloExiste = temaRepository.existsByTituloAndIdNot(temaActualizarDTO.getTitulo(), id);
        boolean mensajeExiste = temaRepository.existsByMensajeAndIdNot(temaActualizarDTO.getMensaje(), id);

        if (tituloExiste ) {
            throw new BadRequestExcepton("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestExcepton("El mensaje ya exisite!");
        }

        try{
            if (tema != null){
                tema.setTitulo(temaActualizarDTO.getTitulo());
                tema.setMensaje(temaActualizarDTO.getMensaje());
                tema.setGenero(temaActualizarDTO.getGenero());
                tema.setUpdatedAt(LocalDateTime.now());
                tema = temaRepository.save(tema);
            }else {
                throw new BadRequestExcepton("ERROR ACTUALIZAR: No se pudo actualizar tema!");
            }
        }catch (DataAccessException e){
            throw new BadRequestExcepton("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        return manejoRespuestaCliente(tema, false);
    }

    @Override
    public Boolean delete(Integer id) {
        temaRepository.deleteById(id);
        return true;
    }

    private TemaDto manejoRespuestaCliente(Tema tema, boolean incluirRespuestas) {
        TemaDto temaDto = new ModelMapper().map(tema,TemaDto.class);
        if (incluirRespuestas && tema.getRespuestas() != null && !tema.getRespuestas().isEmpty()) {
            List<RespuestaTemaDTO> respuestasDto = tema.getRespuestas().stream()
                    .map(this::manejoRespuesta) // Método para mapear Respuesta a RespuestaDTO
                    .collect(Collectors.toList());
            temaDto.setRespuestas(respuestasDto);
        }
        return temaDto;
    }

    // Método para mapear Respuesta a RespuestaDTO
    private RespuestaTemaDTO manejoRespuesta(Respuesta respuesta) {
        RespuestaTemaDTO respuestaDto = new ModelMapper().map(respuesta,RespuestaTemaDTO.class);

        return respuestaDto;
    }

}
