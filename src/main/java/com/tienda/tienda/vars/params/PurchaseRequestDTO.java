package com.tienda.tienda.vars.params;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseRequestDTO {
    @NotNull
    @Range(min = 0, message = "Porfavor, ingrese un id valido")
    private Long product_id;

    @NotNull
    @Range(min = 0, message = "La cantidad solicitada no puede ser negativo")
    private Integer quantity;
}
