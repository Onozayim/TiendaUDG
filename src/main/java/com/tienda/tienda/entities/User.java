package com.tienda.tienda.entities;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tienda.tienda.annotations.PasswordMatching;
import com.tienda.tienda.annotations.StrongPassword;

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
@PasswordMatching(
    password = "password",
    confirmPassword = "confirm_password",
    message = "Las contraseñas deben coincidir"
)
public class User implements UserDetails {
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

    @Column(nullable = false, unique = false, length = 50)
    private String roles = "USER";

    @Column(nullable = false, unique = true, length = 75)
    @Size(min = 0, max = 75, message = "La contraseña tiene un límite de 75 caracteres")
    @NotBlank(message = "La contraseña es necesaria")
    @StrongPassword
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("get Authorities");
        List<GrantedAuthority> authorities = Arrays.stream(roles.split(";"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println(authorities);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
