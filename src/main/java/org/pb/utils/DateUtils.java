package org.pb.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public final class DateUtils {
    public static LocalDate getFirstDayOfMonthFromCurrentDate(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getLastDayOfMonthFromCurrentDate(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.lastDayOfMonth());
    }
}
