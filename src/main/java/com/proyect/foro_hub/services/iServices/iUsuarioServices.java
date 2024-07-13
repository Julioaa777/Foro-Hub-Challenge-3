package com.proyect.foro_hub.services.iServices;
import com.proyect.foro_hub.models.dtos.usuario.UsuarioDTO;
import com.proyect.foro_hub.models.dtos.usuario.UsuarioRegistroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface iUsuarioServices {

    List<UsuarioDTO> findAll();
    Page<UsuarioDTO> paginate(Pageable pageable);
    UsuarioDTO findById(Integer id);
    UsuarioDTO save(UsuarioRegistroDTO usuarioRegistroDTO);
    UsuarioDTO update(Integer id, UsuarioRegistroDTO usuarioRegistroDTO);
//    Boolean delete(Integer id);
    ResponseEntity eliminarUsuario(Integer id);

}
