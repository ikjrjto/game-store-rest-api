package com.sumdu.edu.ua.ivan.gamestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameDto {

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, message = "Game title should have at least 2 characters")
    private String title;

    @NotBlank(message = "Must not be empty")
    @Size(min = 4, message = "Game genre should have at least 4 characters")
    private String genre;

    @NotBlank(message = "Must not be empty")
    private String imageUrl;
}
