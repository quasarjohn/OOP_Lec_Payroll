package values;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

/**
 * Created by John on 1/8/2017.
 */
public class Strings {

    public static ObservableList<String> hours() {

        ObservableList<String> hours = FXCollections.observableArrayList(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12"
        );
        return hours;
    }

    public static ObservableList<String> minutes() {

        ObservableList<String> hours = FXCollections.observableArrayList(
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",
                "41",
                "42",
                "43",
                "44",
                "45",
                "46",
                "47",
                "48",
                "49",
                "50",
                "51",
                "52",
                "53",
                "54",
                "55",
                "56",
                "57",
                "58",
                "59"
        );
        return hours;
    }

    public static ObservableList<String> days() {

        ObservableList<String> hours = FXCollections.observableArrayList(
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31"
        );
        return hours;
    }

    public static ObservableList<String> months () {
        ObservableList<String> months = FXCollections.observableArrayList(
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
        );
        return months;
    }

    public static ObservableList<String> years() {
        ObservableList<String> years = FXCollections.observableArrayList();

        for(int i = 0; i < 100; i++) {
            years.add(Integer.toString(1990 + i));
        }
        return years;
    }

    public static String preDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyMM");
        return format.format(new Date());
    }

    public static DecimalFormat df() {

        return new DecimalFormat("0.00");
    }

    public static final ObservableList<String> orderOptions() {
        ObservableList<String> orderOptions = FXCollections.observableArrayList(
                "Order by First Name",
                "Order by Last Name",
                "Order by Employee Number"
        );
        return orderOptions;
    }

    public static final ObservableList<String> showSelectionOptions() {
        ObservableList<String> showSelectionOptions = FXCollections.observableArrayList(
                "Show Active",
                "Show Inactive",
                "Show Both"
        );
        return showSelectionOptions;
    }

    public static final ObservableList<String> showAttendanceSelectionOptions() {
        ObservableList<String> showSelectionOptions = FXCollections.observableArrayList(
                "Show Active w/ Sched",
                "Show Active",
                "Show Inactive",
                "Show Both"
        );
        return showSelectionOptions;
    }

    public static final ObservableList<String> showDashboardSelectionOptions() {
        ObservableList<String> showSelectionOptions = FXCollections.observableArrayList(
                "Show Active & Timed in",
                "Show Active",
                "Show Inactive",
                "Show Both"
        );
        return showSelectionOptions;
    }

    public static final ObservableList<String> attendanceTableHeader() {
        ObservableList<String> attendanceTableHeader = FXCollections.observableArrayList(
                "DATE", "SCHEDULE", "TIMEIN", "TIMEOUT", "HOURS WORKED", "STATUS"
        );

        return attendanceTableHeader;
    }

    public static final SimpleDateFormat getDateFormat() {

        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static final ObservableList<String> dashboardIndividialHeader() {
        ObservableList<String> attendanceTableHeader = FXCollections.observableArrayList(
                "CATEGORY", "AMOUNT", "COMMISSION PERCENTAGE", "COMMISSION RECEIVED", "NOTES"
        );

        return attendanceTableHeader;
    }
}
