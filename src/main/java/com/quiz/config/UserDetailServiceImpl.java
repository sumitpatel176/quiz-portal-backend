package com.quiz.config;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quiz.entity.User;
import com.quiz.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Repository se Optional data nikalna
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        // 2. Agar user nahi milta toh exception throw karna, aur mil jata hai toh .get() se nikalna
        User user = userOptional.orElseThrow(() -> 
            new UsernameNotFoundException("User not found with username: " + email)
        );
        
        // 3. Wrapper class mein send karna
        return new CustomUserDetails(user);
    }
}