package com.example.ChatApplicationPowderedByAI.Service;

import com.example.ChatApplicationPowderedByAI.DTO.DisplayRegisterUserDTO;
import com.example.ChatApplicationPowderedByAI.DTO.SignInDTO;
import com.example.ChatApplicationPowderedByAI.DTO.UserRegisterDTO;
import com.example.ChatApplicationPowderedByAI.Entites.BlockedTokensEntity;
import com.example.ChatApplicationPowderedByAI.Entites.UserEntity;
import com.example.ChatApplicationPowderedByAI.Exception.AlreadyExistException;
import com.example.ChatApplicationPowderedByAI.Exception.CommonException;
import com.example.ChatApplicationPowderedByAI.Repository.BlockedTokenRepository;
import com.example.ChatApplicationPowderedByAI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BlockedTokenRepository blockedTokenRepository;

    public DisplayRegisterUserDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())){
            throw new AlreadyExistException("Username already existed try another one");
        }
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())){
            throw new AlreadyExistException("Email already existed try another one");
        }
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(userRegisterDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userEntity.setEmail(userRegisterDTO.getEmail());
        userEntity.setChatBotName(userRegisterDTO.getChatBotName());
        userRepository.save(userEntity);
        return new DisplayRegisterUserDTO(
                userEntity.getId(),
                userRegisterDTO.getUsername(),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getChatBotName(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public String signIn(SignInDTO signInDTO) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getUsername(),signInDTO.getPassword()));
        if (authentication.isAuthenticated()){
            String authToken=jwtService.generateToken(signInDTO.getUsername());
            return authToken;
        }
        else{
            throw new CommonException("Unable to login");
        }
    }

    public String deleteUser(String username) {
        UserEntity userEntity=userRepository.findByUsername(username).orElseThrow(()->new CommonException("Unable to delete the user"));
        userRepository.delete(userEntity);
        return "User deleted successfully";
    }

    public String signOut(String authHeader) {
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            String token=authHeader.substring(7);
            if (!blockedTokenRepository.existsByToken(token)){
                BlockedTokensEntity blockedTokensEntity=new BlockedTokensEntity();
                blockedTokensEntity.setToken(token);
                BlockedTokensEntity blockedTokensEntity1=blockedTokenRepository.save(blockedTokensEntity);
                if (blockedTokensEntity1==null){
                    throw new CommonException("Unable to logout");
                }
            }

        }
        return "Logged out successfully";
    }
}
