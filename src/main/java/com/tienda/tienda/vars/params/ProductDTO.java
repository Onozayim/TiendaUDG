package com.tienda.tienda.vars.params;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    @Size(min = 0, max = 25, message = "El nombre del producto tiene un límite de 25 caracteres")
    @NotBlank(message = "El nombre del producto es necesario")
    private String name;

    @Size(min = 0, max = 255, message = "La descripcion del producto tiene un límite de 255 caracteres")
    @NotBlank(message = "La descripcion del producto es necesario")
    private String description;

    @NotNull(message = "El costo del producto es necesario")
    @Range(min = 0, max = 99999, message = "El stock debe de ser entre 0 - 99999")
    private Float cost;

    @NotNull(message = "El stock del producto es necesario")
    @Range(min = 0, max = 99999, message = "El stock debe de ser entre 0 - 99999")
    private Integer stock;
}
