package com.ekaua28.gamesorting.app.dto;

import com.ekaua28.gamesorting.app.enums.GameType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSortingRequestTest {

    private GameDto gameDto;

    private GameSortingRequest victim;

    @BeforeEach
    void setUp() {
        victim = new GameSortingRequest();
        gameDto = GameDto.builder()
                .name("Sunday1 Game")
                .start(LocalDateTime.parse("2021-02-14T16:30:00"))
                .type(GameType.V64)
                .build();
    }

    @Test
    void shouldSetGetGameList() throws IOException {
        List<GameDto> list = List.of(gameDto);
        victim.setGameList(list);
        assertEquals(list, victim.getGameList());
    }
}
