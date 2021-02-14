package com.ekaua28.gamesorting.app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GameSortingRequest {

    @Valid
    @NotNull
    private @Getter @Setter List<GameDto> gameList;
}
