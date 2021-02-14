package com.ekaua28.gamesorting.app.dto;

import com.ekaua28.gamesorting.app.enums.GameType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = GameDto.GameDtoBuilder.class)
public class GameDto {

    String name;

    GameType type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime start;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class GameDtoBuilder {

    }

}
