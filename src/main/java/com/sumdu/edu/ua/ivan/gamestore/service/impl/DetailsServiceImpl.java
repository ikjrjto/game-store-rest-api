package com.sumdu.edu.ua.ivan.gamestore.service.impl;

import com.sumdu.edu.ua.ivan.gamestore.dto.DetailsDto;
import com.sumdu.edu.ua.ivan.gamestore.entity.Details;
import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import com.sumdu.edu.ua.ivan.gamestore.exception.ResourceNotFoundException;
import com.sumdu.edu.ua.ivan.gamestore.exception.StoreAPIException;
import com.sumdu.edu.ua.ivan.gamestore.repository.DetailsRepository;
import com.sumdu.edu.ua.ivan.gamestore.repository.GameRepository;
import com.sumdu.edu.ua.ivan.gamestore.service.DetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepository detailsRepository;
    private final GameRepository gameRepository;
    private final ModelMapper mapper;

    @Override
    public DetailsDto createDetails(Long gameId, DetailsDto detailsDto) {

        Details details = mapToEntity(detailsDto);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game", "id", gameId)
                );

        details.setGame(game);
        Details newDetails = detailsRepository.save(details);

        return mapToDto(newDetails);
    }

    @Override
    public List<DetailsDto> getDetailsByGameId(Long gameId) {
        List<Details> details = detailsRepository.findByGameId(gameId);
        return details.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public DetailsDto getDetailsById(Long gameId, Long detailsId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game", "id", gameId));

        Details details = detailsRepository.findById(detailsId)
                .orElseThrow(() -> new ResourceNotFoundException("Details", "id", detailsId));

        if (!details.getGame().getId().equals(game.getId())) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Details doesn't belong to game");
        }

        return mapToDto(details);
    }

    @Override
    public DetailsDto updateDetails(Long gameId, Long detailsId, DetailsDto detailsDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game", "id", gameId));

        Details details = detailsRepository.findById(detailsId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Details", "id", detailsId));

        if (!details.getGame().getId().equals(game.getId())) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Details doesn't belong to game");
        }

        details.setPublisher(detailsDto.getPublisher());
        details.setEngine(detailsDto.getEngine());
        details.setCountry(detailsDto.getCountry());

        Details updatedDetails = detailsRepository.save(details);
        return mapToDto(updatedDetails);
    }

    @Override
    public void deleteDetails(Long gameId, Long detailsId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game", "id", gameId));

        Details details = detailsRepository.findById(detailsId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Details", "id", detailsId));

        if (!details.getGame().getId().equals(game.getId())) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Details doesn't belong to game");
        }

        detailsRepository.delete(details);
    }

    private Details mapToEntity(DetailsDto detailsDto) {
        return mapper.map(detailsDto, Details.class);
    }

    private DetailsDto mapToDto(Details newDetails) {
        return mapper.map(newDetails, DetailsDto.class);
    }
}
