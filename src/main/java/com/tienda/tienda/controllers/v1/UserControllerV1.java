package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.entities.User;
import com.tienda.tienda.responses.JsonResponses;
import com.tienda.tienda.services.AuthenticationService;
import com.tienda.tienda.services.JwtService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.vars.StringConsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "v1/user")
public class UserControllerV1 {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    JwtService jwtService;
    @Autowired
    JsonResponses jsonResponses;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<?> getME() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return jsonResponses.ReturnOkData(currentUser, StringConsts.Done);
    }
}
