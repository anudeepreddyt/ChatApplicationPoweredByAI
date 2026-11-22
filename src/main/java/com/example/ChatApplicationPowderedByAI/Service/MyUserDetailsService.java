package com.example.ChatApplicationPowderedByAI.Service;

import com.example.ChatApplicationPowderedByAI.Entites.UserEntity;
import com.example.ChatApplicationPowderedByAI.Entites.UserPrincipal;
import com.example.ChatApplicationPowderedByAI.Exception.CommonException;
import com.example.ChatApplicationPowderedByAI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findByUsername(username).orElseThrow(()->new CommonException("Unable to user"));
        return new UserPrincipal(userEntity);
    }
}
