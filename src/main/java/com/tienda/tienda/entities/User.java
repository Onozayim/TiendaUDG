package com.tienda.tienda.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EnableJpaAuditing
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 0, max = 50, message = "El nombre de usuario tiene un límite de 50 caracteres")
    @NotBlank(message = "El nombre de usuario es necesario")
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 0, max = 100, message = "El correo tiene un límite de 100 caracteres")
    @NotBlank(message = "El correo es necesario")
    @Email(message = "El correo es invalido")
    private String email;

    @Column(nullable = false, unique = true, length = 75)
    @Size(min = 0, max = 75, message = "La contraseña tiene un límite de 75 caracteres")
    @NotBlank(message = "La contraseña es necesaria")
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    @UpdateTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp updated_at;

    @Transient
    @Size(min = 0, max = 75, message = "La contraseña tiene un límite de 75 caracteres")
    @NotBlank(message = "Se debe confirmar la contraseña")
    private String confirm_password;
}
