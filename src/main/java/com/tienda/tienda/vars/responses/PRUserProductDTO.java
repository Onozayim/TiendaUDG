package com.tienda.tienda.vars.responses;

import com.tienda.tienda.entities.PurchaseRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PRUserProductDTO {
    public PRUserProductDTO(PurchaseRequest purchaseRequest) {
        this.product = new ProductUserDTO(purchaseRequest.getProduct());
        this.user = new UserDTO(purchaseRequest.getUser());
    }

    private UserDTO user;

    private ProductUserDTO product;
}
