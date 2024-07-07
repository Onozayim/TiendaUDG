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
@Table(name = "custom_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EnableJpaAuditing
public class CustomRequest extends BaseEntity {

    @Column(nullable = false, unique = false, length = 255)
    private String description;

    @Column(nullable = true, unique = false)
    private Float cost;

    @Column(nullable = false, unique = false, columnDefinition = "CHAR(1)")
    private Character status = 'R';

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
