package com.tienda.tienda.services;

import com.tienda.tienda.entities.PurchaseRequest;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.exceptions.PrNotPending;

public interface PurchaseRequestService {
    public PurchaseRequest savePurchaseRequest(PurchaseRequest purchaseRequest);
    public PurchaseRequest acceptPurchaseRequest(User user, Long id) throws PrNotPending;
    public PurchaseRequest rejectPurchaseRequest(User user, Long id) throws PrNotPending;
    public PurchaseRequest findPurchaseRequest(Long id);
}
