package utils;

import com.jfoenix.controls.JFXComboBox;
import values.Strings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by John on 1/11/2017.
 */
public class DatePickerUtils {

    public static int wordToInt(String month) {

        for(int i = 0; i < Strings.months().size(); i++) {
            if(month.equalsIgnoreCase(Strings.months().get(i)))
                return i + 1;
        }
        return 0;
    }

    public static int[] dateToInt(String date) {
        String[] dates = date.trim().split("-");
        int[] intDates = new int[3];
        intDates[0] = Integer.parseInt(dates[0]);
        intDates[1] = Integer.parseInt(dates[1]);
        intDates[2] = Integer.parseInt(dates[2]);

        return intDates;
    }

    public static int getCurrentMonth() {
        return DatePickerUtils.dateToInt(new SimpleDateFormat("yyyy-MM-dd").
                format(new Date()))[1];
    }
}
