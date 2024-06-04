package dev.manish.userservice.controllers;

import dev.manish.userservice.dtos.LoginRequestDto;
import dev.manish.userservice.dtos.LogoutRequestDto;
import dev.manish.userservice.dtos.SignUpRequestDto;
import dev.manish.userservice.dtos.SignUpResponseDto;
import dev.manish.userservice.exceptions.UserNotFoundException;
import dev.manish.userservice.models.Token;
import dev.manish.userservice.models.User;
import dev.manish.userservice.services.UserService;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        /**
         * check if email and pass is in DB
         * if yes create the token(use random string ) return the token
         * else
         * return some error
         */
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
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

//    public ResponseEntity<Void> logout(){
//        /**
//         * Delete the token if exists -> 200
//         * if doesnot exist give 404
//         * */
//        return null;
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        // delete token if exists -> 200
        // if doesn't exist give a 404

        userService.logout(logoutRequestDto.getToken());
        return ResponseEntity.ok().build(); // or throw an exception, based on your error handling policy
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

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable("token") @NonNull String token) throws UserNotFoundException {
        return userService.validateToken(token);
    }
}
