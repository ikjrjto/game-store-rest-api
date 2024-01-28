package com.sumdu.edu.ua.ivan.gamestore.repository;

import com.sumdu.edu.ua.ivan.gamestore.entity.Details;
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
class DetailsRepositoryTest {

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private GameRepository gameRepository;


    @Test
    public void DetailsRepository_Save_ReturnSavedDetails(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details = new Details();
        details.setPublisher("Test Publisher 1");
        details.setEngine("Test Engine 1");
        details.setCountry("Test Country 1");
        details.setGame(game);

        // Act
        gameRepository.save(game);
        Details saved = detailsRepository.save(details);

        // Assert
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    public void  DetailsRepository_GetAll_ReturnMoreThanOneDetails(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details1 = new Details();
        details1.setPublisher("Test Publisher 1");
        details1.setEngine("Test Engine 1");
        details1.setCountry("Test Country 1");
        details1.setGame(game);

        Details details2 = new Details();
        details2.setPublisher("Test Publisher 2");
        details2.setEngine("Test Engine 2");
        details2.setCountry("Test Country 2");
        details2.setGame(game);

        // Act
        gameRepository.save(game);
        detailsRepository.save(details1);
        detailsRepository.save(details2);

        List<Details> detailsList = detailsRepository.findAll();

        // Assert
        Assertions.assertThat(detailsList).isNotNull();
        Assertions.assertThat(detailsList.size()).isEqualTo(2);
    }

    @Test
    public void  DetailsRepository_GetById_ReturnDetailsNotNull(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details1 = new Details();
        details1.setPublisher("Test Publisher 1");
        details1.setEngine("Test Engine 1");
        details1.setCountry("Test Country 1");
        details1.setGame(game);

        // Act
        gameRepository.save(game);
        detailsRepository.save(details1);

        Details details = detailsRepository.findById(details1.getId()).get();

        // Assert
        Assertions.assertThat(details).isNotNull();
    }

    @Test
    public void  DetailsRepository_GetByGameId_ReturnMoreThanOneDetails(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details1 = new Details();
        details1.setPublisher("Test Publisher 1");
        details1.setEngine("Test Engine 1");
        details1.setCountry("Test Country 1");
        details1.setGame(game);

        Details details2 = new Details();
        details2.setPublisher("Test Publisher 2");
        details2.setEngine("Test Engine 2");
        details2.setCountry("Test Country 2");
        details2.setGame(game);

        // Act
        gameRepository.save(game);
        detailsRepository.save(details1);
        detailsRepository.save(details2);

        List<Details> byGameId = detailsRepository.findByGameId(game.getId());

        // Assert
        Assertions.assertThat(byGameId).isNotNull();
        Assertions.assertThat(byGameId.size()).isEqualTo(2);
    }

    @Test
    public void  DetailsRepository_UpdateDetails_ReturnDetailsNotNull(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details1 = new Details();
        details1.setPublisher("Test Publisher 1");
        details1.setEngine("Test Engine 1");
        details1.setCountry("Test Country 1");
        details1.setGame(game);

        // Act
        gameRepository.save(game);
        detailsRepository.save(details1);

        Details details = detailsRepository.findById(details1.getId()).get();
        details.setPublisher("Test Publisher 1 upd");

        Details updatedDetails = detailsRepository.save(details);

        // Assert
        Assertions.assertThat(updatedDetails).isNotNull();
        Assertions.assertThat(updatedDetails.getPublisher()).isNotNull();
    }

    @Test
    public void  DetailsRepository_DeleteDetails_ReturnDetailsIsEmpty(){
        // Arrange
        Game game = new Game();
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

        Details details1 = new Details();
        details1.setPublisher("Test Publisher 1");
        details1.setEngine("Test Engine 1");
        details1.setCountry("Test Country 1");
        details1.setGame(game);

        // Act
        gameRepository.save(game);
        detailsRepository.save(details1);

        detailsRepository.deleteById(details1.getId());
        Optional<Details> deletedDetails = detailsRepository.findById(details1.getId());

        // Assert
        Assertions.assertThat(deletedDetails).isEmpty();
    }
}