package com.tienda.tienda.vars.params;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    @NotBlank(message = "Favor de ingresar el correo")
    @Size(min = 0, max = 100, message = "El correo tiene un límite de 100 caracteres")
    @Email(message = "El correo es invalido")
    private String email;

    @Size(min = 0, max = 75, message = "La contraseña tiene un límite de 75 caracteres")
    @NotBlank(message = "La contraseña es necesaria")
    private String password;
}
