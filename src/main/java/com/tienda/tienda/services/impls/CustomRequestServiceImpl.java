package com.tienda.tienda.services.impls;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.CustomRequest;
import com.tienda.tienda.repositories.CustomRequestRepository;
import com.tienda.tienda.services.CustomRequestService;

@Service
public class CustomRequestServiceImpl implements CustomRequestService{
    @Autowired
    CustomRequestRepository customRequestRepository;
    
    @Override
    public CustomRequest saveCustomRequest(CustomRequest customRequest) {
        return customRequestRepository.save(customRequest);
    }


    @Override
    public CustomRequest getCustomRequest(Long id) {
        return customRequestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Petición no encontrada"));
    }
}
