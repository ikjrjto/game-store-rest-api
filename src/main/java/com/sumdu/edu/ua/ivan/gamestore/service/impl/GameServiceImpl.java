package com.sumdu.edu.ua.ivan.gamestore.service.impl;

import com.sumdu.edu.ua.ivan.gamestore.dto.GameDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.GameResponse;
import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import com.sumdu.edu.ua.ivan.gamestore.exception.ResourceNotFoundException;
import com.sumdu.edu.ua.ivan.gamestore.repository.GameRepository;
import com.sumdu.edu.ua.ivan.gamestore.service.GameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final ModelMapper mapper;

    @Override
    public GameDto createGame(GameDto gameDto) {
        Game newGame = repository.save(mapToEntity(gameDto));
        return mapToDto(newGame);
    }

    @Override
    public GameResponse getAllGames(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Game> games = repository.findAll(pageable);

        List<Game> gameList = games.getContent();

        List<GameDto> content = gameList
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return mapToResponse(content, games);
    }

    @Override
    public GameDto getGameById(Long id) {
        Game game = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game", "id", id)
        );
        return mapToDto(game);
    }

    @Override
    public GameDto updateGame(GameDto gameDto, Long id) {
        Game game = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game", "id", id)
        );

        game.setTitle(gameDto.getTitle());
        game.setGenre(gameDto.getGenre());
        game.setImageUrl(gameDto.getImageUrl());

        Game updatedGame = repository.save(game);
        return mapToDto(updatedGame);
    }

    @Override
    public void deleteGame(Long id) {
        Game game = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game", "id", id)
        );
        repository.delete(game);
    }

    private GameDto mapToDto(Game newGame) {
        return mapper.map(newGame, GameDto.class);
    }

    private Game mapToEntity(GameDto gameDto) {
        return mapper.map(gameDto, Game.class);
    }

    private GameResponse mapToResponse(List<GameDto> content, Page<Game> games) {
        return new GameResponse(
                content,
                games.getNumber(),
                games.getSize(),
                games.getTotalElements(),
                games.getTotalPages(),
                games.isLast()
        );
    }
}
