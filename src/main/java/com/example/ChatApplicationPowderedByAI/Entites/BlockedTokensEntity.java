package com.example.ChatApplicationPowderedByAI.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockedTokensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private LocalDateTime blockedAt;

    @PrePersist
    protected void onCreated(){
        this.blockedAt=LocalDateTime.now();
    }
}
