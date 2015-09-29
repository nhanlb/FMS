/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Chien Do Xo
 */
public class DateUtil {

    public static java.sql.Timestamp getSqlTimestamp(java.util.Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date.getTime()));
        c.set(Calendar.MILLISECOND, 0);

        return new java.sql.Timestamp(c.getTimeInMillis());
    }
    ////////////////////////////////////////////////////////////////////////

    public static java.sql.Date getSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
    ////////////////////////////////////////////////////////////////////////

    public static String convertDateToString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    ////////////////////////////////////////////////////////////////////////

    public static String convertDateTimeToString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
    ////////////////////////////////////////////////////////////////////////

    public static String convertDateToString1(java.util.Date date) {
        if (date == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    ////////////////////////////////////////////////////////////////////////

    public static Date getFirstDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);

        return calendar.getTime();
    }
    ////////////////////////////////////////////////////////////////////////

    public static Date getLastDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        return lastDayOfMonth;
    }
    
    public static java.sql.Timestamp convertTimestamp(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }
}
