package com.sumdu.edu.ua.ivan.gamestore.repository;

import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Test
    public void GameRepository_Save_ReturnSavedGame() {
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        // Act
        Game savedGame = repository.save(game);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
        Assertions.assertThat(savedGame.getId()).isGreaterThan(0);
    }

    @Test
    public void GameRepository_FindAll_ReturnMoreThanOneGame(){
        // Arrange
        Game game1 = new Game();
        game1.setTitle("Test Game 1");
        game1.setGenre("Test Genre 1");
        game1.setImageUrl("Test image url 1");

        Game game2 = new Game();
        game2.setTitle("Test Game 2");
        game2.setGenre("Test Genre 2");
        game2.setImageUrl("Test image url 2");

        // Act
        repository.save(game1);
        repository.save(game2);

        List<Game> gameList = repository.findAll();

        // Assert
        Assertions.assertThat(gameList).isNotNull();
        Assertions.assertThat(gameList.size()).isEqualTo(2);
    }

    @Test
    public void GameRepository_FindById_ReturnGame(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        // Act
        repository.save(game);

        Game retrievedGame = repository.findById(game.getId()).get();

        // Assert
        Assertions.assertThat(retrievedGame).isNotNull();
    }

    @Test
    public void GameRepository_UpdateGame_ReturnGameNotNull(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        // Act
        repository.save(game);

        Game retrievedGame = repository.findById(game.getId()).get();
        retrievedGame.setTitle("Test Game 1 upd");

        Game updatedGame = repository.save(retrievedGame);
        // Assert
        Assertions.assertThat(updatedGame).isNotNull();
        Assertions.assertThat(updatedGame.getTitle()).isNotNull();
    }

    @Test
    public void GameRepository_DeleteGame_ReturnGameIsEmpty(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        // Act
        repository.save(game);

        repository.deleteById(game.getId());
        Optional<Game> gameReturn = repository.findById(game.getId());

        // Assert
        Assertions.assertThat(gameReturn).isEmpty();
    }

}
