package com.tienda.tienda.services;

import com.tienda.tienda.entities.CustomRequest;

public interface CustomRequestService {
    public CustomRequest saveCustomRequest(CustomRequest customRequest);
    public CustomRequest getCustomRequest(Long id);
}
