package dev.manish.userservice.services;

import dev.manish.userservice.models.User;
import dev.manish.userservice.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepo userRepository;


    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public User signUp(String name, String email, String password) {
        User u = new User();

        u.setName(name);
        u.setEmail(email);
        u.setHashedPassword(password);

        User user = userRepository.save(u);

        return null;
    }
}
