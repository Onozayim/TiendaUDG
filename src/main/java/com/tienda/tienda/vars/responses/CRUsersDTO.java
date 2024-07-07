package com.tienda.tienda.vars.responses;

import com.tienda.tienda.entities.CustomRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CRUsersDTO {

    public CRUsersDTO(CustomRequest customRequest) {
        this.id = customRequest.getId();
        this.description = customRequest.getDescription();
        this.cost = customRequest.getCost();
        this.status = customRequest.getStatus();
        this.buyer = new UserDTO(customRequest.getBuyer());
        this.seller = new UserDTO(customRequest.getSeller());
    }

    private Long id;
    private String description;
    private Float cost;
    private Character status;
    private UserDTO buyer;
    private UserDTO seller;
}
