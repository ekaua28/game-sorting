package com.ekaua28.gamesorting.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "configuration.big-game")
@Data
public class BigGameTypeDaysConfig {
    private Map<String, List<String>> typeDays = new HashMap<>();

    private List<String> dates = new ArrayList<>();

    public Map<String, List<String>> getTypeDays() {
        return this.typeDays;
    }

    public List<String> getDates() {
        return this.dates;
    }
}
