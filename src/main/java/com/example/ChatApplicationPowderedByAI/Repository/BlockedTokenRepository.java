package com.example.ChatApplicationPowderedByAI.Repository;

import com.example.ChatApplicationPowderedByAI.Entites.BlockedTokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedTokenRepository extends JpaRepository<BlockedTokensEntity,Integer> {
    boolean existsByToken(String token);
}
