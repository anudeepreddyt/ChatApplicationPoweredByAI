package com.example.ChatApplicationPowderedByAI.Controller;

import com.example.ChatApplicationPowderedByAI.DTO.DisplayRegisterUserDTO;
import com.example.ChatApplicationPowderedByAI.DTO.SignInDTO;
import com.example.ChatApplicationPowderedByAI.DTO.UserRegisterDTO;
import com.example.ChatApplicationPowderedByAI.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registeruser")
    @Operation(summary = "Registering new user")
    public ResponseEntity<DisplayRegisterUserDTO> registerUser(@Valid  @RequestBody UserRegisterDTO userRegisterDTO){
        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    @PostMapping("/signin")
    @Operation(summary = "Sign In")
    public ResponseEntity<String> signIn(@Valid @RequestBody SignInDTO signInDTO){
        return ResponseEntity.ok(userService.signIn(signInDTO));
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<String> deleteUser(Authentication authentication){
        String username=authentication.getName();
        return ResponseEntity.ok(userService.deleteUser(username));
    }

    @GetMapping("/signout")
    public ResponseEntity<String> signOut(@RequestHeader("Authorization")String authHeader){
        return ResponseEntity.ok(userService.signOut(authHeader));
    }


}
