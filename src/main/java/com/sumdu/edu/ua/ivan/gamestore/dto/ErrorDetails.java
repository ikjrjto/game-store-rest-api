package com.sumdu.edu.ua.ivan.gamestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;
}
