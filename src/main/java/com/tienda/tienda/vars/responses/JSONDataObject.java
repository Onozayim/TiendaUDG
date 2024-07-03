package com.tienda.tienda.vars.responses;

import lombok.Data;

@Data
public class JSONDataObject {
    private Object data;
    public String message;
    public String status;
}
