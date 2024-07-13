package com.proyect.foro_hub.models.dtos.usuario;
import com.proyect.foro_hub.models.dtos.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private String filePerfil;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
