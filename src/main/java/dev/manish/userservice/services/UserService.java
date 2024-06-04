package dev.manish.userservice.services;

import dev.manish.userservice.exceptions.UserNotFoundException;
import dev.manish.userservice.models.Token;
import dev.manish.userservice.models.User;
import dev.manish.userservice.repositories.TokenRepo;
import dev.manish.userservice.repositories.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepo userRepository;
    private TokenRepo tokenRepository;


    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepository, TokenRepo tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        if(bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            Token token = new Token();

            token.setUser(user);
            token.setValue(RandomStringUtils.randomAlphanumeric(128));
            LocalDate today = LocalDate.now();
            LocalDate oneDayLater = today.plusDays(1);

            Date expiryAt = Date.from(oneDayLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //token.setExpiryAt(System.currentTimeMillis()+(1000*60*60*24));
            token.setExpiryAt(expiryAt);
            //return new Token(user.getId());
            //return token;
            return tokenRepository.save(token);
        }
        return null;
    }

    public User signUp(String name, String email, String password) {
        //Validation

        User u = new User();

        u.setName(name);
        u.setEmail(email);
        //u.setHashedPassword(password);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);

        return user;
    }

    /*
    API Call
    http://localhost:8080/users/logout
        {
        "token": "heAoVFgk7Z8yVqI8RQxaEsw5HDvQZsbSZI3qFiC0EouDXnGDyijljfYiPnlBRxjFtGs3rZYXAegyS5rW485rYFefO4lxiKwkU2E1QIEbRAEybJHbvVaMNrWI2OVuXrDe"
        }
     */

    public void logout(String token) {
        //We are actually loggingOut a Token insted of user
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEquals(token, false);

        if(token1.isEmpty()){
            //return null;
        }

        Token t = token1.get();
        t.setDeleted(true);
        tokenRepository.save(t);
    }

    // http://localhost:8080/users/validate/TybOTWY5QAHP4v4W9rwp60CSFZZz9K6x1BKTOPUZhlTSevgQz0k4Lonqs6NyyCkAPdZHWuchafSbiDXmPuD75beDmJarK8Np3JC52JH51Jxihsv6J3oBwx9TUhaB4PF4
    public User validateToken(String token) throws UserNotFoundException {
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEquals(token, false);

        if(token1.isEmpty()){
            //return null;
           throw new UserNotFoundException("Token not found");
        }

        Token t = token1.get();
        if (t.getExpiryAt().before(new Date())) {
           // return null;
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token has expired");
            throw new UserNotFoundException("Token is expired");
        }

        return t.getUser();
    }
}
