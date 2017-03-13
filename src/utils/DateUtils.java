package utils;

import com.jfoenix.controls.JFXDatePicker;
import connection.AppConnection;
import model.dataStructure.Employee;
import values.Strings;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by John on 1/11/2017.
 */
public class DateUtils {

    public static int wordToInt(String month) {

        for (int i = 0; i < Strings.months().size(); i++) {
            if (month.equalsIgnoreCase(Strings.months().get(i)))
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

    public static int timeToInt(String time) {
        int sec = 0;
        try {
            String[] parts = time.split(":");

            sec = Integer.parseInt(parts[2]) + Integer.parseInt(parts[1]) * 60 + Integer.parseInt(parts[0]) * 3600;
        } catch (Exception e) {
            return 0;
        }

        return sec;
    }

    public static int getCurrentMonth() {
        return DateUtils.dateToInt(new SimpleDateFormat("yyyy-MM-dd").
                format(new Date()))[1];
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getHoursWorked(Employee emp, JFXDatePicker dp) {

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        int timeOut = 0;

        conn.doSomething("select timeout from attendance where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno = " + emp.getPost_empNo() + " AND workdate = '" +
                DateUtils.getDateFromDatePicker(dp) + "'");
        try {
            conn.query();
            while (conn.getRS().next()) {
                timeOut = DateUtils.timeToInt(conn.getRS().getString(1));
                System.out.println(timeOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int timeIn = DateUtils.timeToInt(emp.getTimein());

        if (timeIn == 0) {
            return "00:00:00";
        }

        if(timeOut == 0) {
            timeOut = DateUtils.timeToInt(DateUtils.getCurrentTime());
        }
        DecimalFormat df = new DecimalFormat("00");

        int diff = timeOut - timeIn;

        int hours = 0, min = 0;

        while (diff > 59) {
            diff -= 60;
            min++;

            while (min > 59) {
                min -= 60;
                hours++;
            }
        }

        return df.format(hours) + ":" + df.format(min) + ":" + df.format(diff);
    }

    public static String getDateFromDatePicker(JFXDatePicker dp) {
        return dp.getValue().getYear() + "-" + dp.getValue().getMonthValue() + "-" + dp.getValue().getDayOfMonth();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getTimeDifference(String start, String end) {
        int difference = timeToInt(end) - timeToInt(start);
        return intToTime(difference);
    }

    public static String intToTime(int time) {
        int hour = 0, min = 0;

        while (time > 59) {
            time -= 59;
            min++;
            {
                while (min >= 59) {
                    min -= 59;
                    hour++;
                }
            }
        }

        return hour + ":" + min + ":" + time;
    }
}
