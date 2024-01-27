package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.GameDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.GameResponse;

public interface GameService {

    GameDto createGame(GameDto gameDto);

    GameResponse getAllGames(int pageNo, int pageSize, String sortBy, String sortDir);

    GameDto getGameById(Long id);

    GameDto updateGame(GameDto gameDto, Long id);

    void deleteGame(Long id);
}
