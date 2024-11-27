package com.joelblanc.AppEcityclic.controller;

import com.joelblanc.AppEcityclic.dto.AuthRequest;
import com.joelblanc.AppEcityclic.dto.AuthResponse;
import com.joelblanc.AppEcityclic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            boolean isAuthenticated = userService.authenticate(authRequest.getUsername(), authRequest.getPassword());
            if (isAuthenticated) {
                String token = userService.generateToken(authRequest.getUsername());
                return ResponseEntity.ok(new AuthResponse(token));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
        return ResponseEntity.status(401).body("Usuari o contrasenya incorrectes");
    }
}