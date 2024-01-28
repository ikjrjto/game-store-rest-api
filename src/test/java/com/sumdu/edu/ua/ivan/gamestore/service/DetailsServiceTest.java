package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.DetailsDto;
import com.sumdu.edu.ua.ivan.gamestore.entity.Details;
import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import com.sumdu.edu.ua.ivan.gamestore.repository.DetailsRepository;
import com.sumdu.edu.ua.ivan.gamestore.repository.GameRepository;
import com.sumdu.edu.ua.ivan.gamestore.service.impl.DetailsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailsServiceTest {

    private final long id = 1L;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private DetailsRepository detailsRepository;
    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private DetailsServiceImpl service;

    private Game game;
    private Details details;
    private DetailsDto detailsDto;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setId(id);
        game.setTitle("Test Game 1");
        game.setGenre("Test Genre 1");
        game.setImageUrl("Test image url 1");

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
    public void DetailsService_Create_ReturnDetailsDto() {
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(detailsRepository.save(Mockito.any(Details.class))).thenReturn(details);

        DetailsDto savedDetails = service.createDetails(id, detailsDto);

        Assertions.assertThat(savedDetails).isNotNull();
    }

    @Test
    public void DetailsService_GetByGameId_ReturnDetailsDtoList() {
        when(detailsRepository.findByGameId(id)).thenReturn(Collections.singletonList(details));

        List<DetailsDto> detailsDtoList = service.getDetailsByGameId(id);

        Assertions.assertThat(detailsDtoList).isNotNull();
    }

    @Test
    public void DetailsService_GetById_ReturnDetailsDto() {
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(detailsRepository.findById(id)).thenReturn(Optional.of(details));

        DetailsDto detailsDto = service.getDetailsById(id, id);

        Assertions.assertThat(detailsDto).isNotNull();
    }

    @Test
    public void DetailsService_Update_ReturnDetailsDto() {
        details.setPublisher("Test Publisher 1 upd");

        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(detailsRepository.findById(id)).thenReturn(Optional.of(details));
        when(detailsRepository.save(details)).thenReturn(details);

        DetailsDto updatedResponse = service.updateDetails(id, id, detailsDto);

        Assertions.assertThat(updatedResponse).isNotNull();
    }

    @Test
    public void DetailsService_Delete_ReturnDetailsDto() {
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(detailsRepository.findById(id)).thenReturn(Optional.of(details));

        assertAll(() -> service.deleteDetails(id, id));
    }
}
