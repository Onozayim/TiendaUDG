package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.annotations.ValidId;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.services.AuthenticationService;
import com.tienda.tienda.services.JwtService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.utils.AuthUtils;
import com.tienda.tienda.vars.StringConsts;
import com.tienda.tienda.vars.responses.JsonResponses;
import com.tienda.tienda.vars.responses.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        User currentUser = AuthUtils.getUserAuthenticated();

        return jsonResponses.ReturnOkData(new UserDTO(currentUser), StringConsts.Done);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = {"/delete/", "/delete/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(
        @PathVariable("id") @ValidId String id) {
            userService.deleteUser(Long.valueOf(id));
            return jsonResponses.ReturnOkMessage("Usuario eliminado");
        }
}
