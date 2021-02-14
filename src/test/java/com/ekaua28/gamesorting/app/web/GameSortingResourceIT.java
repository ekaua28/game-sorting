package com.ekaua28.gamesorting.app.web;

import com.ekaua28.gamesorting.app.dto.GameDto;
import com.ekaua28.gamesorting.app.dto.GameSortingRequest;
import com.ekaua28.gamesorting.app.service.GameSortingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@WebFluxTest(controllers = GameSortingResource.class)
public class GameSortingResourceIT {

    @MockBean
    private GameSortingService service;

    @Autowired
    private WebTestClient webTestClient;

    private final GameDto dto = GameDto.builder().name("test").build();
    private final List<GameDto> list = List.of(dto);
    private GameSortingRequest request = new GameSortingRequest();

    @Test
    void shouldSort() {
        given(service.sortingGame(list)).willReturn(list);
        request.setGameList(list);

        webTestClient.post()
                .uri("/api/v1/game-sorting")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk();

        then(service).should().sortingGame(List.of(dto));
    }

}
