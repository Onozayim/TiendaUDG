package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.annotations.ValidId;
import com.tienda.tienda.entities.Product;
import com.tienda.tienda.entities.PurchaseRequest;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.exceptions.PrNotPending;
import com.tienda.tienda.services.ProductService;
import com.tienda.tienda.services.PurchaseRequestService;
import com.tienda.tienda.vars.params.CreatePrDTO;
import com.tienda.tienda.vars.responses.JsonResponses;
import com.tienda.tienda.vars.responses.ProductUserDTO;
import com.tienda.tienda.vars.responses.PurchaseRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/v1/pr")
public class PurchaseRequestControllerV1 {
    @Autowired
    ProductService productService;
    @Autowired
    JsonResponses jsonResponses;
    @Autowired
    PurchaseRequestService purchaseRequestService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSavePR(@RequestBody @Valid CreatePrDTO entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Product product = productService.findProduct(entity.getProduct_id());

        if (product.getStock() < entity.getQuantity())
            return jsonResponses.ReturnErrorMessage("Stock insuficiente", HttpStatus.BAD_REQUEST);

        PurchaseRequest purchaseRequest = new PurchaseRequest();

        purchaseRequest.setProduct(product);
        purchaseRequest.setQuantity(entity.getQuantity());
        purchaseRequest.setUser(user);

        purchaseRequestService.savePurchaseRequest(purchaseRequest);

        // UPDATE PRODUCT STOCK
        product.setStock(product.getStock() - purchaseRequest.getQuantity());
        productService.saveProduct(product);
        return jsonResponses.ReturnOkData(new ProductUserDTO(product), "Solicitud de compra Creado");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping(value = { "/accept/",
            "/accept/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postAcceptPR(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) throws PrNotPending {
        PurchaseRequest purchaseRequest = purchaseRequestService.acceptPurchaseRequest(Long.valueOf(id));
        return jsonResponses.ReturnOkData(new PurchaseRequestDTO(purchaseRequest), "Solicitud de compra aceptada");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping(value = { "/reject/",
            "/reject/{id}" },  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRejectPR(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) throws PrNotPending {
        PurchaseRequest purchaseRequest = purchaseRequestService.rejectPurchaseRequest(Long.valueOf(id));
        Product product = productService.findProduct(purchaseRequest.getProduct().getId());

        product.setStock(product.getStock() + purchaseRequest.getQuantity());
        productService.saveProduct(product);

        return jsonResponses.ReturnOkData(new PurchaseRequestDTO(purchaseRequest), "Solicitud de compra aceptada");
    }
}
