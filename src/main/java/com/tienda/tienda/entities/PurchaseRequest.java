package com.tienda.tienda.entities;


import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "purchase_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EnableJpaAuditing
public class PurchaseRequest extends BaseEntity {
    @Column(nullable = false, unique = false, columnDefinition = "INT(5)")
    private Integer quantity;

    @Column(nullable = false, unique = false, columnDefinition = "CHAR(1)")
    private Character status = 'P';

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
