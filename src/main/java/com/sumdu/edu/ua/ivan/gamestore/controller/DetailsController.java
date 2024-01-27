package com.sumdu.edu.ua.ivan.gamestore.controller;

import com.sumdu.edu.ua.ivan.gamestore.dto.DetailsDto;
import com.sumdu.edu.ua.ivan.gamestore.service.DetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsService service;

    @PostMapping("/games/{gameId}/details")
    public ResponseEntity<DetailsDto> createDetails(@PathVariable Long gameId,
                                                    @Valid @RequestBody DetailsDto detailsDto) {
        return new ResponseEntity<>(service.createDetails(gameId, detailsDto), HttpStatus.CREATED);
    }

    @GetMapping("/games/{gameId}/details")
    public ResponseEntity<List<DetailsDto>> getDetailsByGameId(@PathVariable Long gameId) {
        return ResponseEntity.ok(service.getDetailsByGameId(gameId));
    }

    @GetMapping("/games/{gameId}/details/{detailsId}")
    public ResponseEntity<DetailsDto> getDetailsById(@PathVariable Long gameId,
                                                     @PathVariable Long detailsId) {
        return ResponseEntity.ok(service.getDetailsById(gameId, detailsId));
    }

    @PutMapping("/games/{gameId}/details/{detailsId}")
    public ResponseEntity<DetailsDto> updateDetails(@PathVariable Long gameId,
                                                    @PathVariable Long detailsId,
                                                    @Valid @RequestBody DetailsDto detailsDto) {
        return new ResponseEntity<>(service.updateDetails(gameId, detailsId, detailsDto), HttpStatus.OK);
    }

    @DeleteMapping("/games/{gameId}/details/{detailsId}")
    public ResponseEntity<String> deleteDetails(@PathVariable Long gameId,
                                                @PathVariable Long detailsId) {
        service.deleteDetails(gameId, detailsId);
        return new ResponseEntity<>("Details deleted successfully", HttpStatus.OK);
    }
}
