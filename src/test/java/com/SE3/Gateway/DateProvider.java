package com.SE3.Gateway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateProvider {
    public static String getToday() {
       LocalDateTime today = LocalDateTime.now();
       return today.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 19);
    }

    public static String getYesterday() {
       LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
       return yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 19);
    }

    public static String getTomorrow() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        return tomorrow.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 19);
    }
}
