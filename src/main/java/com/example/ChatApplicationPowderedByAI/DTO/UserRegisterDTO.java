package com.example.ChatApplicationPowderedByAI.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {


    @NotBlank(message = "username shouldn't be null")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$",message = "Must contain only lowercase and numbers")
    private String username;
    @NotBlank(message = "password shouldn't be null")
    @Size(min = 5,max = 10)
    private String password;
    @NotBlank(message = "email shouldn't be null")
    private String email;
    @Column(nullable = false)
    @Builder.Default
    private String chatBotName="AI Assistant";



}
