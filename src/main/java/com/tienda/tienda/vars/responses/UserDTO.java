package com.tienda.tienda.vars.responses;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.tienda.tienda.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        authorities = user.getAuthorities();
    }

    private Long id;
    private String username;
    private String email;
    Collection<? extends GrantedAuthority> authorities;
}
