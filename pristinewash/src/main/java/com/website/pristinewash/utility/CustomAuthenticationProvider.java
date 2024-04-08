package com.website.pristinewash.utility;

import com.website.pristinewash.serviceImp.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    com.website.pristinewash.serviceImp.OTPService OTPService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        String otp = (String) authentication.getCredentials();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(phoneNumber);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid phone number or OTP");
        }

        if(!OTPService.verifyOTP(phoneNumber, otp)) {
            throw new BadCredentialsException("Invalid OTP, Please try again!!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
