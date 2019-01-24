package com.ingsw1718.ingswinspectorapp.common;

/**
 * Created by Raffolox on 26/02/2018.
 */

public class Date {
    public static final String DATE_PATTERN_DDMMYYYY = "dd/mm/yyyy";
    public static final String DATE_PATTERN_YYYYMMDD = "yyyy-mm-dd";

    private int year;
    private int month;
    private int day;

    public static Date parse(String date) {
        int day, month, year;
        if (date.contains("/")) {
            day = Integer.parseInt(date.substring(0, 2));
            month = Integer.parseInt(date.substring(3, 5));
            year = Integer.parseInt(date.substring(6, 10));
        }
        else if (date.contains("-")) {
            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(5, 7));
            day = Integer.parseInt(date.substring(8, 10));
        }
        else {
            throw new IllegalArgumentException(date);
        }
        return new Date(day, month, year);
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(long millis) {
        this.day = 1;
        this.month = 1;
        this.year = 1970;
        long days = millis/1000/60/60/24;
        for (int i=0; i<days; i++) {
            this.toNextDay();
        }
    }

    private void toNextDay() {
        day++;
        if (day == 29 && month == 2 && !isBisestile(year)) {
            month++;
            day = 1;
        }
        else if (day == 30 && month == 2 && isBisestile(year)) {
            month++;
            day = 1;
        }
        else if (day == 31 && (month == 11 || month == 4 || month == 6 || month == 9)) {
            month++;
            day = 1;
        }
        else if (day == 32) {
            month++;
            day = 1;
        }
        if (month == 13) {
            month = 1;
            year++;
        }
    }

    private boolean isBisestile(int year) {
        return (year>1584 && ((year%400==0) || (year%4==0 && year%100!=0)));
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getTwoDigitMonth() {
        String tmp = "0" + month;
        return tmp.substring(tmp.length()-2);
    }

    public int getDay() {
        return day;
    }

    public String getTwoDigitDay() {
        String tmp = "0" + day;
        return tmp.substring(tmp.length()-2);
    }

    public String toString() {
        return toString(DATE_PATTERN_DDMMYYYY);
    }

    public String toString(String pattern) {
        if (pattern.equals(DATE_PATTERN_DDMMYYYY)) {
            return getTwoDigitDay()+"/"+getTwoDigitMonth()+"/"+year;
        }
        else if (pattern.equals(DATE_PATTERN_YYYYMMDD)) {
            return year+"-"+getTwoDigitMonth()+"-"+getTwoDigitDay();
        }
        else {
            return toString(DATE_PATTERN_DDMMYYYY);
        }
    }

    public boolean equals(Object o) {
        return o!=null && o instanceof Date && ((Date) o).day == day && ((Date) o).month == month && ((Date) o).year == year;
    }
}
