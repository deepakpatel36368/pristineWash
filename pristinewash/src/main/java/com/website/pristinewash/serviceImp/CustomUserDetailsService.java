package com.website.pristinewash.serviceImp;

import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        CustomUserDetails customUserDetails = null;
        if(user != null) {
            customUserDetails = new CustomUserDetails();
            customUserDetails.setUser(user);
        } else {
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        return customUserDetails;
    }


    public String saveOTP(String username, String password) {
        User user = userRepository.findByUsername(username);
        CustomUserDetails customUserDetails = null;
        if(user != null) {
            user.setPassword(password);
            userRepository.save(user);
            return "Otp can be verified now";
        } else {
            throw new UsernameNotFoundException("User not found with user name : " + username);
        }
    }
}
