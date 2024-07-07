package com.tienda.tienda.vars.params;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostDTO {
    @NotNull(message = "Favor de ingresar el costo de la oferta") 
    @Range(min = 0, message = "El costo de la oferta no puede ser negativo")
    Float cost;
}
