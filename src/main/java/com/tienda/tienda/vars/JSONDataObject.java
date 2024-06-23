package com.tienda.tienda.vars;

import lombok.Data;

@Data
public class JSONDataObject<T> {
    private T data;
    public String message;
    public String status;
}
