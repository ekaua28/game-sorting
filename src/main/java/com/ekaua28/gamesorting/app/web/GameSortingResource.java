package com.ekaua28.gamesorting.app.web;

import com.ekaua28.gamesorting.app.dto.GameDto;
import com.ekaua28.gamesorting.app.dto.GameSortingRequest;
import com.ekaua28.gamesorting.app.service.GameSortingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/game-sorting")
public class GameSortingResource {

    private final GameSortingService service;

    public GameSortingResource(GameSortingService service) {
        this.service = service;
    }

    @PostMapping
    public Flux<GameDto> sort(@Valid @RequestBody GameSortingRequest request){
        return Flux.defer(() -> Flux.fromIterable(service.sortingGame(request.getGameList())));
    }
}
