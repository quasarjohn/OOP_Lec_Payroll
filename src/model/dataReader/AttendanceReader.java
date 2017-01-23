package model.dataReader;

import connection.AppConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dataStructure.Employee;

import java.sql.SQLException;
import java.util.Observable;

/**
 * Created by John on 1/20/2017.
 */
public class AttendanceReader {

    private AppConnection conn;

    public ObservableList<ObservableList<String>> getEmpAttendance(Employee emp, String month, String year) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        conn = new AppConnection();
        conn.connectToDB();
        conn.loadDriver();

        conn.doSomething("select workdate, schedule_time, timein, timeout,hours_worked, status from attendance " +
                "where year(workdate) = '" + year + "' and month(workdate) = '" + month + "' " +
                "and pre_empno = " + emp.getPre_empNo() + " " +
                "and post_empno =" + emp.getPost_empNo());
        try {
            conn.query();
            while (conn.getRS().next()) {
                ObservableList<String> row = FXCollections.observableArrayList();

                String workDate = conn.getRS().getString(1);
                String schedule = conn.getRS().getString(2);
                String  timein= conn.getRS().getString(3);
                String timeout = conn.getRS().getString(4);
                String hoursWorked = conn.getRS().getString(5);
                String status = conn.getRS().getString(6);

                if (timein == null) {
                    timein = "";
                }
                if(status == null) {
                    status = "";
                }

                if(schedule == null) {
                    schedule = "";
                }

                if(timeout == null) {
                    timeout = "";
                }

                if(hoursWorked == null) {
                    hoursWorked = "";
                }

                row.addAll(
                        workDate,
                        schedule,
                        timein,
                        timeout,
                        hoursWorked,
                        status);
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
