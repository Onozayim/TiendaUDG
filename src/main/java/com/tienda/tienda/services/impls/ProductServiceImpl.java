package com.tienda.tienda.services.impls;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.tienda.entities.Product;
import com.tienda.tienda.repositories.ProductRepository;
import com.tienda.tienda.services.ProductService;
import com.tienda.tienda.vars.params.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product saveProduct(Product product) {
        product.setAvailable_stock(product.getStock());
        return productRepository.save(product);
    }

    @Override
    public Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.findProduct(id);

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCost(productDTO.getCost());
        product.setStock(productDTO.getStock());

        return this.saveProduct(product);
    }
}
