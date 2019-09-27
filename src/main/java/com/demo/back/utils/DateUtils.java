package com.demo.back.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName DateUtils
 * @Author yakun.shi
 * @Date 2019/5/29 13:11
 * @Version 1.0
 **/
public class DateUtils {

    public static String dateToString(LocalDateTime dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTimeFormatter.format(dateTime);
        return format;
    }
}
