package dev.manish.userservice.controllers;

import dev.manish.userservice.dtos.SignUpRequestDto;
import dev.manish.userservice.dtos.SignUpResponseDto;
import dev.manish.userservice.models.User;
import dev.manish.userservice.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    public User login(){
        /**
         * check if email and pass is in DB
         * if yes create the token(use random string ) return the token
         * else
         * return some error
         */
        return null;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto){
        /**
         * no need to hash the password now
         * just store user as it is in the DB
         * and also no need for email verification either
         * */
        //return null;

        return toSignUpResponseDto(userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword()));
    }

    public ResponseEntity<Void> logout(){
        /**
         * Delete the token if exists -> 200
         * if doesnot exist give 404
         * */
        return null;
    }

    public SignUpResponseDto toSignUpResponseDto(User user){
        if(user == null){
            return null;
        }

        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setEmailVerified(user.isEmailVerified());

        return responseDto;
    }
}
