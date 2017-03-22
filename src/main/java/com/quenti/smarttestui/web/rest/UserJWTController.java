package com.quenti.smarttestui.web.rest;
import com.quenti.smarttestui.components.LoginQuentiComponent;
import com.quenti.smarttestui.security.UserDetailsService;
import com.quenti.smarttestui.security.jwt.JWTConfigurer;
import com.quenti.smarttestui.security.jwt.TokenProvider;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.UserQuentiDTO;
import com.quenti.smarttestui.web.rest.vm.LoginVM;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.web.rest.vm.ManagedUserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserJWTController {


    private LoginQuentiComponent requestComponent;
    private UserDetailsService uds;

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserService userService;

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/authenticate/quenti")
    @Timed
    public ResponseEntity<?> authorizeQuenti(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        LoginQuentiComponent loginQuentiComponent = new LoginQuentiComponent();
        UserQuentiDTO uqDTO = loginQuentiComponent.init(loginVM);
        if (!uqDTO.getObjTokenDTO().equals("null")) {
            Optional usuarioReal = userService.getUserWithAuthoritiesByLogin(loginVM.getUsername());
            if (usuarioReal.toString().equals("Optional.empty")) {
                ManagedUserVM muVM =
                    new ManagedUserVM(null,loginVM.getUsername(),loginVM.getPassword(), uqDTO.getFirstName(),
                        uqDTO.getLastName(),"quenti@localhost",true,null,null,"system",
                        ZonedDateTime.now(),"system",ZonedDateTime.now());
                userService.createUser(muVM);
            }

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());


            try {
                Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
                String jwt = tokenProvider.createToken(authentication, rememberMe);
                response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
                return ResponseEntity.ok(new JWTToken(jwt));
            } catch (AuthenticationException exception) {
                return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
            }
        }
        return null;
    }
}





//    login quenti
//
//            if login success then
//                if user exists in the db
//                not
//                create user mannagedVM
//                activo
//                else
//                return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
//
//    }
