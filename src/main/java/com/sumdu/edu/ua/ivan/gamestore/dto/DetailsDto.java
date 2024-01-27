package com.sumdu.edu.ua.ivan.gamestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DetailsDto {

    @NotBlank(message = "Publisher shouldn't be empty")
    private String publisher;

    @NotBlank(message = "Engine shouldn't be empty")
    private String engine;

    @NotBlank(message = "Country shouldn't be empty")
    private String country;
}
