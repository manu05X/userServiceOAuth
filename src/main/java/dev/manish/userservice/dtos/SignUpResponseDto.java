package dev.manish.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private String name;
    private String email;
    private boolean isEmailVerified;
}
