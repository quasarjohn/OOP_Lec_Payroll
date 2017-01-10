package model.dataWriter;

import connection.AppConnection;
import model.dataStructure.Employee;

import java.sql.SQLException;

/**
 * Created by John on 1/11/2017.
 */
public class EmployeeAdder {

    private static AppConnection conn;

    public static void addEmployee(Employee emp) {

        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("insert into employees values('" +
                emp.getPre_empNo() + "'," +
                "LPAD(NEXT VALUE FOR empNo_seq, 3,'1'),'" +
                emp.getLastName() + "','" +
                emp.getFirstName() + "','" +
                emp.getMiddleName() + "','" +
                emp.getGender() + "','" +
                emp.getPhoneNumber() + "','" +
                emp.getAddress() + "','" +
                emp.getContactPerson() + "','" +
                emp.getContactPersonNumber() + "','" +
                emp.getContactPersonAddress() + "','" +
                emp.getBirthDate() + "','" +
                emp.getHireDate() + "','" +
                emp.getSchedule() + "','" +
                emp.getTime() + "')"
        );

        try {
            conn.update();
            System.out.println("DATA WRITE COMPLETED");
        } catch (SQLException e) {
            System.out.println("DATA WRITE FAILED");
            e.printStackTrace();
        }
        conn.closeConnection();
    }
}
