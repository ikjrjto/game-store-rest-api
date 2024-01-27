package com.sumdu.edu.ua.ivan.gamestore.service;

import com.sumdu.edu.ua.ivan.gamestore.dto.DetailsDto;

import java.util.List;

public interface DetailsService {

    DetailsDto createDetails(Long gameId, DetailsDto detailsDto);

    List<DetailsDto> getDetailsByGameId(Long gameId);

    DetailsDto getDetailsById(Long gameId, Long detailsId);

    DetailsDto updateDetails(Long gameId, Long detailsId, DetailsDto detailsDto);

    void deleteDetails(Long gameId, Long detailsId);
}
