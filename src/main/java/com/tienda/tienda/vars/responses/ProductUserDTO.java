package com.tienda.tienda.vars.responses;

import com.tienda.tienda.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUserDTO {

    public ProductUserDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.cost = product.getCost();
        this.stock = product.getStock();

        this.user = new UserDTO(product.getUser());
    }

    private String name;

    private String description;

    private Float cost;

    private Integer stock;

    private UserDTO user;
}
