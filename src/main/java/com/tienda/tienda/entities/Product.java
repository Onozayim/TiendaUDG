package com.tienda.tienda.entities;

import java.security.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Data
@EnableJpaAuditing
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @Column(nullable = false, unique = false, length = 25)
    @Size(min = 0, max = 25, message = "El nombre del producto tiene un límite de 25 caracteres")
    @NotBlank(message = "El nombre del producto es necesario")
    private String name;

    @Column(nullable = false, unique = false, length = 255)
    @Size(min = 0, max = 255, message = "La descripcion del producto tiene un límite de 255 caracteres")
    @NotBlank(message = "La descripcion del producto es necesario")
    private String description;

    @Column(nullable = false, unique = false)
    @NotBlank(message = "El costo del producto es necesario")
    private Float cost;

    @Column(nullable = false, unique = false, columnDefinition = "INT(5) UNSIGNED")
    @NotBlank(message = "El stock del producto es necesario")
    private Integer stock;

    @Column(nullable = false, unique = false)
    private Float available_stock;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    @UpdateTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp updated_at;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;
}
