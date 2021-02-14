package com.ekaua28.gamesorting.app.service;

import com.ekaua28.gamesorting.app.config.BigGameTypeDaysConfig;
import com.ekaua28.gamesorting.app.dto.GameDto;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class GameSortingServiceImpl implements GameSortingService {

    private static final String DAY_MONTH_PATTERN = "dd/MM";

    private static final WeekFields WEEK_FIELDS = WeekFields.of(DayOfWeek.MONDAY, 7);

    private final Comparator<GameDto> comparator = Comparator.comparing(this::getYearPerFirstDayAtWeek)
            .thenComparing(this::getWeekYear)
            .thenComparing(this::todayDateGameOrderWeight)
            .thenComparingInt(this::bigGameOrderWeight)
            .thenComparing(GameDto::getStart);

    private final BigGameTypeDaysConfig bigGameTypeDaysConfig;

    public GameSortingServiceImpl(BigGameTypeDaysConfig bigGameTypeDaysConfig) {
        this.bigGameTypeDaysConfig = bigGameTypeDaysConfig;
    }

    /**
     * Method of getting date and month in specific format pattern DAY_MONTH_PATTERN("dd/MM") from LocalDateTime
     *
     * @param date LocalDateTime type
     * @return Date and month string in specific format pattern
     */
    private String getDateMonth(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DAY_MONTH_PATTERN));
    }

    /**
     * Method of getting year relative to the first day of the week relative to game start date
     *
     * @param game GameDto type
     * @return int year
     */
    private int getYearPerFirstDayAtWeek(GameDto game) {
        return game.getStart().with(ChronoField.DAY_OF_WEEK, 1).getYear();
    }

    /**
     * Method of getting week of year based on game start date
     *
     * @param game GameDto type
     * @return int week of year
     */
    private int getWeekYear(GameDto game) {
        return game.getStart().get(WEEK_FIELDS.weekOfWeekBasedYear());
    }

    /**
     * Method return order weight of game based on game start date relative to today date
     *
     * @param game GameDto type
     * @return int order weight
     */
    private int todayDateGameOrderWeight(GameDto game) {
        return LocalDate.now().compareTo(game.getStart().toLocalDate()) == 0 ? -1 : 0;
    }

    /**
     * Method return order weight of game based on game start date or game type. This method wrapped other method to get order weight
     *
     * @param game GameDto type
     * @return int order weight
     */
    private int bigGameOrderWeight(GameDto game) {
        int i = bigGameDateOrderWeight(game.getStart());
        return i != 0 ? i : bigGameDayTypeOrderWeight(game.getType().toString(), game.getStart().getDayOfWeek().getValue());
    }

    /**
     * Method return order weight of game based on game type and specific day of week.
     *
     * @param gameTypeString game type converted to String type
     * @param DayOfWeekValue game start date converted to day of week in int value
     * @return int order weight
     */
    private int bigGameDayTypeOrderWeight(String gameTypeString, int DayOfWeekValue) {
        Map<String, List<String>> typeDays = bigGameTypeDaysConfig.getTypeDays();
        if (typeDays.isEmpty() || !typeDays.containsKey(gameTypeString) || typeDays.get(gameTypeString).isEmpty()) {
            return 0;
        }
        return typeDays.get(gameTypeString).contains(String.valueOf(DayOfWeekValue)) ? -1 : 0;
    }

    /**
     * Method return order weight of game based on game date.
     *
     * @param date LocalDateTime
     * @return int order weight
     */
    private int bigGameDateOrderWeight(LocalDateTime date) {

        List<String> dates = bigGameTypeDaysConfig.getDates();
        if (dates.isEmpty()) {
            return 0;
        }
        return dates.contains(getDateMonth(date)) ? -1 : 0;
    }

    /**
     * Method to sorting List of game base on specific conditions.
     * The method receives already verified data as input, for this reason the service lacks a number of checks
     *
     * @param gameList List of GameDto to sort
     * @return sorted List of GameDto
     */
    @Override
    public List<GameDto> sortingGame(List<GameDto> gameList) {
        gameList.sort(comparator);
        return gameList;
    }

}
