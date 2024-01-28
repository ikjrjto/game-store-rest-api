package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.GameDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.GameResponse;
import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import com.sumdu.edu.ua.ivan.gamestore.repository.GameRepository;
import com.sumdu.edu.ua.ivan.gamestore.service.impl.GameServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository repository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void PokemonService_Create_ReturnGameDto() {
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        GameDto gameDto = new GameDto();
        gameDto.setTitle(game.getTitle());
        gameDto.setGenre(game.getGenre());
        gameDto.setImageUrl(game.getImageUrl());

        when(repository.save(Mockito.any(Game.class))).thenReturn(game);

        GameDto savedGame = gameService.createGame(gameDto);

        Assertions.assertThat(savedGame).isNotNull();
    }

    @Test
    public void GameService_GetAll_ReturnGameResponse() {
        Page<Game> games = Mockito.mock(Page.class);

        when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(games);

        GameResponse retrievedGameResponse =
                gameService.getAllGames(0, 10, "id", "asc");

        Assertions.assertThat(retrievedGameResponse).isNotNull();
    }

    @Test
    public void GameService_GetById_ReturnGameDto() {
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        when(repository.findById(1L)).thenReturn(Optional.of(game));

        GameDto retrievedGame = gameService.getGameById(1L);

        Assertions.assertThat(retrievedGame).isNotNull();
    }

    @Test
    public void GameService_UpdateGame_ReturnGameDto() {
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        GameDto gameDto = new GameDto();
        gameDto.setTitle(game.getTitle());
        gameDto.setGenre(game.getGenre());
        gameDto.setImageUrl(game.getImageUrl());

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        when(repository.save(Mockito.any(Game.class))).thenReturn(game);

        GameDto updatedDto = gameService.updateGame(gameDto, 1L);

        Assertions.assertThat(updatedDto).isNotNull();
    }

    @Test
    public void GameService_DeleteGame_ReturnGameDto() {
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        when(repository.findById(1L)).thenReturn(Optional.of(game));

        assertAll(() -> gameService.deleteGame(1L));
    }
}
