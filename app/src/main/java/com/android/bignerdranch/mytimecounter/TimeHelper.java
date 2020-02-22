package com.android.bignerdranch.mytimecounter;

import com.android.bignerdranch.mytimecounter.model.Employment;
import com.android.bignerdranch.mytimecounter.model.HasDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeHelper {
    public static String getTime(int time) {
        String result = "";
        int hours = time / 3600;
        int minutes = (time - (hours * 3600)) / 60;
        int seconds = (time - (hours * 3600) - (minutes * 60));
        result = String.valueOf(hours) + ":";
        if (minutes < 10) {
            result += "0" + String.valueOf(minutes) + ":";
        } else {
            result += String.valueOf(minutes) + ":";
        }
        if (seconds < 10) {
            result += "0" + String.valueOf(seconds);
        } else {
            result += String.valueOf(seconds);
        }
        return result;
    }

    public static boolean compareDate(HasDate hasDateElement, Calendar calendar) {
        return hasDateElement.getDate().get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                hasDateElement.getDate().get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                hasDateElement.getDate().get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
    }

    public static String getDateString(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        return format.format(calendar.getTime());

        /*return String.valueOf(calendar.get(Calendar.YEAR)) + " " +
                calendar.get(Calendar.MONTH) + " "
                + calendar.get(Calendar.DAY_OF_MONTH);*/
    }
}
