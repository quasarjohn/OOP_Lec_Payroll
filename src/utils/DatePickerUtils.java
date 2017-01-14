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
}
