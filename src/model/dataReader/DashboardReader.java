package model.dataReader;

import com.jfoenix.controls.JFXDatePicker;
import connection.AppConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dataStructure.Employee;
import utils.DateUtils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by John on 1/26/2017.
 */
public class DashboardReader {
    private static AppConnection conn;

    //get name, hours worked and total earning
    public static ArrayList<Employee> getEmpList(String workDate) {

        System.out.println(workDate);

        ArrayList<Employee> employees = new ArrayList<>();
        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select e.pre_empno, e.post_empno, e.first_name, e.last_name, " +
                "a.timein, e.image_uuid, e.middle_name, e.commission_percentage from employees e left join attendance a on a.pre_empno = e.pre_empno " +
                "and a.post_empno = e.post_empno where a.workdate = '" + workDate + "' and a.timein is not null");
        try {
            conn.query();
            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setFirstName(conn.getRS().getString(3));
                emp.setLastName(conn.getRS().getString(4));
                emp.setTimein(conn.getRS().getString(5));
                emp.setImageUUID(conn.getRS().getString(6));
                emp.setMiddleName(conn.getRS().getString(7));
                emp.setCommission(conn.getRS().getString(8));

                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.closeConnection();
        return employees;
    }

    public static double getTotalCommission(int pre, int post, String date) {
        double commission = 0;
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select sum(commission) from earnings where pre_empno = " + pre + " " +
                " AND post_empno = " + post + " AND workdate = '" + date + "' group by pre_empno, " +
                "post_empno ");
        try {
            conn.query();
            while (conn.getRS().next()) {
                commission = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
        return commission;
    }

    public double[] getTotalEarningAndCommission(String date) {
        double[] data = new double[2];

        data[0] = 0;
        data[1] = 0;

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select sum(amount), sum(commission) " +
                "from earnings where workdate = '" + date + "'");
        try {
            conn.query();
            while (conn.getRS().next()) {
                data[0] = conn.getRS().getDouble(1);
                data[1] = conn.getRS().getDouble(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
        return data;
    }

    public static ObservableList<ObservableList<String>> getIndividualDashboardData(int pre, int post, String date) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select category, amount, commission_percentage, " +
                "commission, notes from earnings " +
                "where pre_empno = " + pre + " and " +
                "post_empno = " + post + " and " +
                "workdate = '" + date + "'");

        try {
            conn.query();
            while (conn.getRS().next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(conn.getRS().getString(1));
                row.add(conn.getRS().getString(2));
                row.add(conn.getRS().getString(3));
                row.add(conn.getRS().getString(4));
                row.add(conn.getRS().getString(5));

                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
        return data;
    }

    public String getTotalEmpHour(Employee emp, String date) {
        String value = "";
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();
        conn.doSomething("select timein from attendance where pre_empno = " + emp.getPre_empNo() +
                " AND post_empno = " + emp.getPost_empNo() + " AND workdate = '" + date + "'");
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String timeout = "";

        conn.doSomething("select timeout from attendance where pre_empno = " + emp.getPre_empNo() +
                " AND post_empno = " + emp.getPost_empNo() + " AND workdate = '" + date + "'");
        try {
            conn.query();
            while (conn.getRS().next()) {
                timeout = conn.getRS().getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(timeout == null) {
            timeout = DateUtils.getCurrentTime();
        }

        String timeDifference = DateUtils.getTimeDifference(value, timeout);
        int totalWorkedInSec = DateUtils.timeToInt(timeDifference);

        conn.closeConnection();
        return new DecimalFormat("##.00").format(totalWorkedInSec * EmpReader.getRatePerSec(emp));
    }

    public double getTotalBasicPay(ArrayList<Employee> employees, JFXDatePicker dp) {

        double value = 0;
        for (Employee employee : employees) {
            double totalHours = Double.parseDouble(getTotalEmpHour(employee, DateUtils.getDateFromDatePicker(dp)));
            value += totalHours;
        }
        System.out.println(value);
        return value;
    }
}
