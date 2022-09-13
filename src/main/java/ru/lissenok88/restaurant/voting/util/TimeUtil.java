package ru.lissenok88.restaurant.voting.util;

import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class TimeUtil {

    public static final LocalTime TIME_LIMIT = LocalTime.of(11, 0);

    public static final ZoneId TIME_ZONE = ZoneId.systemDefault();

    public static Clock clock = Clock.systemDefaultZone();

    public static void setFixedTime(LocalTime fixedTime) {
        clock = Clock.fixed((LocalDateTime.of(LocalDate.now(), fixedTime).atZone(TIME_ZONE).toInstant()), TIME_ZONE);
    }

    public static void setDefaultTime() {
        clock = Clock.systemDefaultZone();
    }

    public static boolean isBeforeTimeLimit() {
        return LocalTime.now(TimeUtil.clock).isBefore(TIME_LIMIT);
    }
}