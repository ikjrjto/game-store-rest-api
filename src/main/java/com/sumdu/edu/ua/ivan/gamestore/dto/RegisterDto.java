package com.sumdu.edu.ua.ivan.gamestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String name;
    private String username;
    private String email;
    private String password;
}
