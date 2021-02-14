package com.ekaua28.gamesorting.app.service;

import com.ekaua28.gamesorting.app.dto.GameDto;

import java.util.List;

public interface GameSortingService {

    List<GameDto> sortingGame(List<GameDto> gameList);
}
