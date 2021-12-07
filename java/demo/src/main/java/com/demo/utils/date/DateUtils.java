package com.demo.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/11/30 13:55
 */
public class DateUtils {

    public static Date getTodayDateTime() {

        String str = getTodayDateStr() + " 00:00:00";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTodayDateStr() {

        Date date = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);

    }

}
