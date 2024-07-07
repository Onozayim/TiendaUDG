package com.tienda.tienda.services.impls;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.PurchaseRequest;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.exceptions.PrNotPending;
import com.tienda.tienda.repositories.PurchaseRequestRepository;
import com.tienda.tienda.services.PurchaseRequestService;

@Service
public class PurchaseRequestServiceImpl implements PurchaseRequestService {
    @Autowired
    PurchaseRequestRepository purchaseRequestRepository;

    @Override
    public PurchaseRequest savePurchaseRequest(PurchaseRequest purchaseRequest) {
        return purchaseRequestRepository.save(purchaseRequest);
    }

    @Override
    public PurchaseRequest acceptPurchaseRequest(User user, Long id) throws PrNotPending {
        PurchaseRequest purchaseRequest = this.findPurchaseRequest(id);

        if(user.getId() != purchaseRequest.getProduct().getUser().getId()) 
            throw new PrNotPending("No tienes el permiso de aceptar la solicitud");

        if(purchaseRequest.getStatus() != 'P')
            throw new PrNotPending("Solicitud de compra no esta pendiente");

        purchaseRequest.setStatus('A');
        
        return purchaseRequestRepository.save(purchaseRequest);
    }

    @Override
    public PurchaseRequest rejectPurchaseRequest(User user, Long id) throws PrNotPending {
        PurchaseRequest purchaseRequest = this.findPurchaseRequest(id);

        if(user.getId() != purchaseRequest.getProduct().getUser().getId()) 
            throw new PrNotPending("No tienes el permiso de rechazar la solicitud");

        if(purchaseRequest.getStatus() != 'P')
            throw new PrNotPending("Solicitud de compra no esta pendiente");

        purchaseRequest.setStatus('R');

        return purchaseRequestRepository.save(purchaseRequest);
    }

    @Override
    public PurchaseRequest findPurchaseRequest(Long id) {
        return purchaseRequestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Solicitud de compra no encontrada"));
    }
}
