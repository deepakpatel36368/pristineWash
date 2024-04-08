package com.website.pristinewash.serviceImp;

import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.UserRepository;
import com.website.pristinewash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found userId " + userId);
        }
    }

    @Override
    public String addUser(User user) {
        userRepository.save(user);
        return user.getUsername() + " saved successfully";
    }

}
