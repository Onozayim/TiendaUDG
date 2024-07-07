package com.tienda.tienda.entities;

import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tienda.tienda.vars.params.ProductDTO;
import com.tienda.tienda.vars.params.UpdateProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@EnableJpaAuditing
public class Product extends BaseEntity {

    public Product(ProductDTO product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.cost = product.getCost();
        this.stock = product.getStock();
    }

    public Product(UpdateProductDTO product) {
        this.setId(product.getId());
        this.name = product.getName();
        this.description = product.getDescription();
        this.cost = product.getCost();
        this.stock = product.getStock();
    }
    
    @Column(nullable = false, unique = false, length = 25)
    @Size(min = 0, max = 25, message = "El nombre del producto tiene un límite de 25 caracteres")
    @NotBlank(message = "El nombre del producto es necesario")
    private String name;

    @Column(nullable = false, unique = false, length = 255)
    private String description;

    @Column(nullable = false, unique = false)
    private Float cost;

    @Column(nullable = false, unique = false, columnDefinition = "INT(5)")
    private Integer stock;

    @Column(nullable = false, unique = false, columnDefinition = "INT(5)")
    private Integer available_stock;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PurchaseRequest> purchase_request;
}
