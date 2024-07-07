package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.annotations.ValidId;
import com.tienda.tienda.entities.Product;
import com.tienda.tienda.entities.PurchaseRequest;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.exceptions.PrNotPending;
import com.tienda.tienda.services.ProductService;
import com.tienda.tienda.services.PurchaseRequestService;
import com.tienda.tienda.utils.AuthUtils;
import com.tienda.tienda.vars.params.PurchaseRequestDTO;
import com.tienda.tienda.vars.responses.JsonResponses;
import com.tienda.tienda.vars.responses.ProductUserDTO;
import com.tienda.tienda.vars.responses.PRUserProductDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> postSavePR(@RequestBody @Valid PurchaseRequestDTO entity) {
        User user = AuthUtils.getUserAuthenticated();

        Product product = productService.findProduct(entity.getProduct_id());

        if(user.getId() == product.getUser().getId())
            return jsonResponses.ReturnErrorMessage("No puedes solicitar tus productos", HttpStatus.BAD_REQUEST);

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

    @PreAuthorize("hasAuthority('SELLER')")
    @PatchMapping(value = { "/accept/",
            "/accept/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchAcceptPR(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) throws PrNotPending {
        PurchaseRequest purchaseRequest = purchaseRequestService.acceptPurchaseRequest(
                AuthUtils.getUserAuthenticated(), Long.valueOf(id));

        return jsonResponses.ReturnOkData(new PRUserProductDTO(purchaseRequest), "Solicitud de compra aceptada");
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PatchMapping(value = { "/reject/",
            "/reject/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchRejectPR(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) throws PrNotPending {
        PurchaseRequest purchaseRequest = purchaseRequestService.rejectPurchaseRequest(AuthUtils.getUserAuthenticated(),
                Long.valueOf(id));
        Product product = productService.findProduct(purchaseRequest.getProduct().getId());

        product.setStock(product.getStock() + purchaseRequest.getQuantity());
        productService.saveProduct(product);

        return jsonResponses.ReturnOkData(new PRUserProductDTO(purchaseRequest), "Solicitud de compra rechazada");
    }
}
