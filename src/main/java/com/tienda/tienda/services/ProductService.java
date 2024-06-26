package com.tienda.tienda.services;


import com.tienda.tienda.entities.Product;
import com.tienda.tienda.vars.params.ProductDTO;

public interface ProductService {
    public Product saveProduct(Product product);
    public Product findProduct(Long id);
    public Product updateProduct(Long id, ProductDTO productDTO);
}
