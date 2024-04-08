package com.website.pristinewash.serviceImp;

import com.website.pristinewash.entity.Role;
import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.UserRepository;
import com.website.pristinewash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OTPService implements com.website.pristinewash.service.OTPService {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public boolean verifyOTP(String phoneNumber, String otp) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(phoneNumber);
        if(userDetails != null && userDetails.getPassword().equals(otp)) {
            return true;
//            implement opt expire verification
//            if (otpRecord.getExpirationTimestamp().isAfter(LocalDateTime.now())) {
//                return true;
//            }
        }
        return false;
    }

    @Override
    public String saveOTP(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            user.setPassword(password);
            userRepository.save(user);
            return "Otp can be verified now";
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            //set all new registered user with USER role
            Role role = new Role();
            role.setRole("USER");
            Set<Role> newUserRole = new HashSet<>();
            newUserRole.add(role);

            newUser.setRoles(newUserRole);

            String response = userService.addUser(newUser);
            throw new UsernameNotFoundException(
                    "Hey, You have been registered with us now. OTP ###" + password +"### has been generated for phone number : " + username);
        }
    }
}
