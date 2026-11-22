package com.example.ChatApplicationPowderedByAI.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,10}$")
    private String username;
    @Size(min = 5,max = 10)
    private String password;
}
