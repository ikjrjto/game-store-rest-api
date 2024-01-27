package com.sumdu.edu.ua.ivan.gamestore.controller;

import com.sumdu.edu.ua.ivan.gamestore.dto.GameDto;
import com.sumdu.edu.ua.ivan.gamestore.dto.GameResponse;
import com.sumdu.edu.ua.ivan.gamestore.service.GameService;
import com.sumdu.edu.ua.ivan.gamestore.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody GameDto gameDto) {
        return new ResponseEntity<>(service.createGame(gameDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GameResponse> getAllGames(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(service.getAllGames(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGameById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@Valid @RequestBody GameDto gameDto, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateGame(gameDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        service.deleteGame(id);
        return new ResponseEntity<>("Game entity deleted successfully", HttpStatus.OK);
    }
}
