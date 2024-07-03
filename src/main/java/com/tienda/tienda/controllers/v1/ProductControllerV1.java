package com.tienda.tienda.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.annotations.ValidId;
import com.tienda.tienda.entities.Product;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.services.ProductService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.vars.params.ProductDTO;
import com.tienda.tienda.vars.responses.JsonResponses;
import com.tienda.tienda.vars.responses.ProductUserDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "v1/products")
public class ProductControllerV1 {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    JsonResponses jsonResponses;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSaveProduct(@Valid @RequestBody ProductDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Product product = new Product(request);
        product.setUser(user);
        productService.saveProduct(product);
        return jsonResponses.ReturnOkData(new ProductUserDTO(product), "Producto creado");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = { "/update/",
            "/update/{id}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putUpdateProduct(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id,
            @Valid @RequestBody ProductDTO entity) {

        Product product = productService.updateProduct(Long.valueOf(id), entity);

        return jsonResponses.ReturnOkData(
                new ProductUserDTO(product),
                "Producto actualizado");
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = { "/delete/", "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDeleteProduct(
            @PathVariable("id") @ValidId String id) {

        productService.deleteProduct(Long.valueOf(id));

        return jsonResponses.ReturnOkMessage(
                "Producto eliminado");
    }
}
