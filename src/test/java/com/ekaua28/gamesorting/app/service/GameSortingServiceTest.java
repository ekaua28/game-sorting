package com.ekaua28.gamesorting.app.service;

import com.ekaua28.gamesorting.app.config.BigGameTypeDaysConfig;
import com.ekaua28.gamesorting.app.dto.GameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@JsonTest
@ExtendWith(MockitoExtension.class)
public class GameSortingServiceTest {

    final ObjectMapper mapper = new ObjectMapper();

    private final static List<String> BIG_GAME_DATE_LIST = List.of("23/12", "25/12", "26/12", "31/12");
    private final static Map<String, List<String>> BIG_GAME_TYPE_DAY_LIST = Map.of("V86", List.of("3"), "V75", List.of("6"), "GS75", List.of("7"));

    @Autowired
    private JacksonTester<List<GameDto>> json;

    @Mock
    BigGameTypeDaysConfig bigGameTypeDaysConfig;

    private GameSortingServiceImpl victim;

    @BeforeEach
    void setUp() {
        victim = new GameSortingServiceImpl(bigGameTypeDaysConfig);
    }

    @Test
    void shouldSortListEmptyList() {
        List<GameDto> gameDtoList = new ArrayList<>();
        assertEquals(gameDtoList, victim.sortingGame(gameDtoList));
    }

    @Test
    void shouldSortOneElementList() throws IOException {
        ObjectContent<List<GameDto>> list = json.read("simple_GameDtoList.json");
        assertEquals(list.getObject(), victim.sortingGame(list.getObject()));
    }

    @Test
    void shouldSortList() throws IOException {
        when(bigGameTypeDaysConfig.getDates())
                .thenReturn(BIG_GAME_DATE_LIST);
        when(bigGameTypeDaysConfig.getTypeDays())
                .thenReturn(BIG_GAME_TYPE_DAY_LIST);
        ObjectContent<List<GameDto>> list = json.read("input_GameDtoList.json");
        ObjectContent<List<GameDto>> list1 = json.read("output_GameDtoList.json");
        list1.assertThat().isEqualTo(victim.sortingGame(list.getObject()));
    }

    @Test
    void shouldSortListWithoutBigGameDates() throws IOException {
        when(bigGameTypeDaysConfig.getDates())
                .thenReturn(List.of());
        when(bigGameTypeDaysConfig.getTypeDays())
                .thenReturn(BIG_GAME_TYPE_DAY_LIST);
        ObjectContent<List<GameDto>> list = json.read("input_GameDtoList.json");
        ObjectContent<List<GameDto>> list1 = json.read("output_without_big_game_GameDtoList.json");
        list1.assertThat().isEqualTo(victim.sortingGame(list.getObject()));
    }

}
