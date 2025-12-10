package utils;

import java.time.LocalDate;

public class Dates {
    public static boolean isBetween(LocalDate target,LocalDate start,LocalDate end){
        boolean isAfterStart = target.isAfter(start) || target.equals(start);
        boolean isBeforeEnd = target.isBefore(end) || target.equals(end);
        return isAfterStart && isBeforeEnd;
    }
}
