package com.ekaua28.gamesorting.app.dto;

import com.ekaua28.gamesorting.app.enums.GameType;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GameDtoIT {

    @Autowired
    private JacksonTester<GameDto> json;

    private GameDto victim;

    @BeforeEach
    void setUp() {
        victim = GameDto.builder()
                .name("Sunday1 Game")
                .start(LocalDateTime.parse("2021-02-14T16:30:00"))
                .type(GameType.V64)
                .build();
    }

    @Test
    void shouldCheckEqualsContract(){
        EqualsVerifier.forClass(GameDto.class).verify();
    }

    @Test
    void shouldCheckToString(){
        ToStringVerifier.forClass(GameDto.class).verify();
    }

    @Test
    void shouldCopy(){
        assertThat(victim.toBuilder().build()).isEqualTo(victim);
    }

    @Test
    void shouldSerialize() throws IOException {
        assertThat(json.write(victim)).isEqualToJson("expected_GameDto.json");
    }

    @Test
    void shouldDeserialize() throws IOException {
        json.read("expected_GameDto.json").assertThat().isEqualTo(victim);
    }
}
