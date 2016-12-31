package domain;

/**
 * Created by John on 12/19/2016.
 */
public class StaticData {

    private static String[] months = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public static String[] getMonths() {
        return months;
    }
}
