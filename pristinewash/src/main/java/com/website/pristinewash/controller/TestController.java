package com.website.pristinewash.controller;

import com.website.pristinewash.entity.User;
import com.website.pristinewash.serviceImp.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/anyone")
    public String accessByAnyone(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the authentication object is not null and is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Extract the username from the authentication object
            log.info(authentication.getName());
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            log.info(customUserDetails.getUsername() + " " + customUserDetails.getUser().getUser_id());
            String username = authentication.getName();
            // Now you have the username
        } else {
            // Authentication object is null or not authenticated
            // Handle the situation accordingly
        }
        return "This is only accessed by Authorised User only. ";
    }

    @GetMapping("/admin")
    public String accessByAdmin() {
        return "This function can only be access by admin";
    }

    @GetMapping("/user")
    public String accessByUser() {
        return "This function can only be access by user";
    }

    @GetMapping("/useradmin")
    public String accessByUserAndAdmin() {
        return "This function can only be access by user and Admin";
    }
}
