package com.sumdu.edu.ua.ivan.gamestore.repository;

import com.sumdu.edu.ua.ivan.gamestore.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
