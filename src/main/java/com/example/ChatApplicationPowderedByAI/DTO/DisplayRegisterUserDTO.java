package com.example.ChatApplicationPowderedByAI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayRegisterUserDTO {

    private Integer userId;
    private String username;
    private String email;
    private String chatBotName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
