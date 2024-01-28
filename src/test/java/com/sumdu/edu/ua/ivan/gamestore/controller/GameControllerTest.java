package com.sumdu.edu.ua.ivan.gamestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumdu.edu.ua.ivan.gamestore.dto.DetailsDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.GameDto;
import com.sumdu.edu.ua.ivan.gamestore.entity.Details;
import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import com.sumdu.edu.ua.ivan.gamestore.security.JwtTokenProvider;
import com.sumdu.edu.ua.ivan.gamestore.service.GameService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    private final long id = 1L;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService service;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider tokenProvider;

    private Game game;
    private GameDto gameDto;
    private Details details;
    private DetailsDto detailsDto;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setId(id);
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        gameDto = new GameDto();
        gameDto.setTitle(game.getTitle());
        gameDto.setGenre(game.getGenre());
        gameDto.setImageUrl(game.getImageUrl());

        details = new Details();
        details.setId(id);
        details.setPublisher("Test Publisher 1");
        details.setEngine("Test Engine 1");
        details.setCountry("Test Country 1");
        details.setGame(game);

        game.setDetails(Set.of(details));

        detailsDto = new DetailsDto();
        detailsDto.setPublisher(details.getPublisher());
        detailsDto.setEngine(details.getEngine());
        detailsDto.setCountry(details.getCountry());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void GameController_CreateGame_ReturnCreated() throws Exception {
        given(service.createGame(ArgumentMatchers.any())).willAnswer(
                (invocation) -> invocation.getArgument(0)
        );
        given(tokenProvider.generateToken(ArgumentMatchers.any()))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(gameDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", CoreMatchers.is(gameDto.getGenre())));

    }
}
