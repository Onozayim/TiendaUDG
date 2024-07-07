package com.tienda.tienda.vars.params;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomRequestDTO {
    @NotBlank(message = "La descripción de la petición es necesaria")
    @Size(min = 0, max = 500, message = "La descripción no puede ser mayor de 500 caracteres")
    private String description;

    // @NotNull(message = "El costo de la petición es necesaria")
    // private Float cost;

    @NotBlank(message = "Favor de ingresar el id del vendedor")
    @Size(min = 0, message = "Favor de ingresar un id válido")
    private String sellerEmail;
}
