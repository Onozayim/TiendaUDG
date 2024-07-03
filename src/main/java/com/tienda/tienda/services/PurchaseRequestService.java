package com.tienda.tienda.services;

import com.tienda.tienda.entities.PurchaseRequest;
import com.tienda.tienda.exceptions.PrNotPending;

public interface PurchaseRequestService {
    public PurchaseRequest savePurchaseRequest(PurchaseRequest purchaseRequest);
    public PurchaseRequest acceptPurchaseRequest(Long id) throws PrNotPending;
    public PurchaseRequest rejectPurchaseRequest(Long id) throws PrNotPending;
    public PurchaseRequest findPurchaseRequest(Long id);
}
