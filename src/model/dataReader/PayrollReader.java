package model.dataReader;

import connection.AppConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dataStructure.Employee;
import utils.DateUtils;

import java.sql.SQLException;

/**
 * Created by John on 3/4/2017.
 */
public class PayrollReader {

    private AppConnection conn;

    public PayrollReader() {
        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();
    }

    public static ObservableList getPaydates() {
        ObservableList<String> paydates = FXCollections.observableArrayList();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select distinct gendate from payroll");
        try {
            conn.query();
            while (conn.getRS().next()) {
                paydates.add(conn.getRS().getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeConnection();
        return paydates;
    }

    public int getWorkdateCount(Employee emp) {
        int value = 0;

        conn.doSomething("select count(workdate) from attendance where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo() + " AND date_paid is  null");
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public double getHoursWorked(Employee emp) {
        double value = 0;

        conn.doSomething("select sum(hours_worked_in_sec) from attendance where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo() + " AND date_paid is  null");
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value / 3600;
    }

    public double getTotalCommission(Employee emp) {
        double value = 0;

        conn.doSomething("select sum(commission) from earnings where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo() + " AND date_paid is  null");
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public double getTotalBasic(Employee emp) {
        double value = 0;

        conn.doSomething("select sum(basic_pay) from attendance where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo() + " AND date_paid is  null");
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public double getSSS(Employee emp) {
        double value = 0;

        conn.doSomething("select sss from employees where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo());
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public double getPagIbig(Employee emp) {
        double value = 0;

        conn.doSomething("select pag_ibig from employees where pre_empno = " +
                emp.getPre_empNo() + " AND post_empno =" + emp.getPost_empNo());
        try {
            conn.query();
            while (conn.getRS().next()) {
                value = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
}
