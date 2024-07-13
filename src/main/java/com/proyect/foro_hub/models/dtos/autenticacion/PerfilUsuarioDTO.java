package com.proyect.foro_hub.models.dtos.autenticacion;
import com.proyect.foro_hub.models.dtos.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilUsuarioDTO {


    private String nombre;
    private String email;
    private String password;
    private Role role;

}
