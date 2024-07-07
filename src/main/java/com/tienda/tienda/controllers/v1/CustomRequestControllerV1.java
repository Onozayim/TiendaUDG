package com.tienda.tienda.controllers.v1;

import org.springframework.web.bind.annotation.RestController;

import com.tienda.tienda.annotations.ValidId;
import com.tienda.tienda.entities.CustomRequest;
import com.tienda.tienda.entities.User;
import com.tienda.tienda.services.CustomRequestService;
import com.tienda.tienda.services.UserService;
import com.tienda.tienda.utils.AuthUtils;
import com.tienda.tienda.vars.params.CostDTO;
import com.tienda.tienda.vars.params.CustomRequestDTO;
import com.tienda.tienda.vars.responses.CRUsersDTO;
import com.tienda.tienda.vars.responses.JsonResponses;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/v1/cr")
public class CustomRequestControllerV1 {
    @Autowired
    JsonResponses jsonResponses;
    @Autowired
    UserService userService;
    @Autowired
    CustomRequestService customRequestService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSaveCR(@RequestBody @Valid CustomRequestDTO body) {
        User buyer = AuthUtils.getUserAuthenticated();
        User seller = userService.findSellerByEmail(body.getSellerEmail());

        if (buyer.getId() == seller.getId())
            return jsonResponses.ReturnErrorMessage("No puedes hacerte una petición a ti mismo",
                    HttpStatus.BAD_REQUEST);

        CustomRequest customRequest = new CustomRequest();

        customRequest.setBuyer(buyer);
        customRequest.setSeller(seller);
        customRequest.setDescription(body.getDescription());
        customRequest.setCost((float) 0.0);

        customRequestService.saveCustomRequest(customRequest);

        return jsonResponses.ReturnOkData(new CRUsersDTO(customRequest), "Petición realizada");
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PatchMapping(value = { "/makeOffer/",
            "/makeOffer/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchMakeOffer(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id,
            @Valid @RequestBody CostDTO body) {

        User user = AuthUtils.getUserAuthenticated();
        CustomRequest customRequest = customRequestService.getCustomRequest(Long.valueOf(id));

        if (customRequest.getSeller().getId() != user.getId() || !customRequest.getStatus().equals('R'))
            return jsonResponses.ReturnErrorMessage("No puedes hacer una oferta para esta petición",
                    HttpStatus.BAD_REQUEST);

        customRequest.setCost(body.getCost());
        customRequest.setStatus('O');

        customRequestService.saveCustomRequest(customRequest);

        return jsonResponses.ReturnOkData(new CRUsersDTO(customRequest), "Oferta realizada");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping(value = { "/acceptOffer/",
            "/acceptOffer/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchAcceptOffer(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) {

        User user = AuthUtils.getUserAuthenticated();
        CustomRequest customRequest = customRequestService.getCustomRequest(Long.valueOf(id));

        if (customRequest.getBuyer().getId() != user.getId() || !customRequest.getStatus().equals('O'))
            return jsonResponses.ReturnErrorMessage("No puedes aceptar está oferta", HttpStatus.BAD_REQUEST);

        customRequest.setStatus('A');

        customRequestService.saveCustomRequest(customRequest);

        return jsonResponses.ReturnOkData(new CRUsersDTO(customRequest), "Oferta aceptada");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping(value = { "/dennyOffer/",
            "/dennyOffer/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchdennytOffer(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) {

        User user = AuthUtils.getUserAuthenticated();
        CustomRequest customRequest = customRequestService.getCustomRequest(Long.valueOf(id));

        if (customRequest.getBuyer().getId() != user.getId() || !customRequest.getStatus().equals('O'))
            return jsonResponses.ReturnErrorMessage("No puedes rechazar está oferta", HttpStatus.BAD_REQUEST);

        customRequest.setStatus('R');

        customRequestService.saveCustomRequest(customRequest);

        return jsonResponses.ReturnOkData(new CRUsersDTO(customRequest), "Oferta rehazada");
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PatchMapping(value = { "/dennyRequest/",
            "/dennyRequest/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchdennytRequest(
            @PathVariable("id") @NotEmpty(message = "Porfavor ingrese un id") @ValidId String id) {

        User user = AuthUtils.getUserAuthenticated();
        CustomRequest customRequest = customRequestService.getCustomRequest(Long.valueOf(id));

        if (customRequest.getSeller().getId() != user.getId() || !customRequest.getStatus().equals('R'))
            return jsonResponses.ReturnErrorMessage("No puedes rechazar está oferta", HttpStatus.BAD_REQUEST);

        customRequest.setStatus('D');

        customRequestService.saveCustomRequest(customRequest);

        return jsonResponses.ReturnOkData(new CRUsersDTO(customRequest), "Oferta rehazada");
    }

}
