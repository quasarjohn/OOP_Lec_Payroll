package model.dataWriter;

import connection.AppConnection;
import model.dataReader.EmpReader;
import model.dataStructure.Employee;
import values.Strings;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by John on 1/11/2017.
 */
public class EmployeeWriter {

    private static AppConnection conn;

    public void addEmployee(Employee emp) {
//        System.out.println(emp.getFirstName());
//        System.out.println(emp.getFirstName());
//        System.out.println(emp.getMiddleName());
//        System.out.println(emp.getGender());
//        System.out.println(emp.getPhoneNumber());
//        System.out.println(emp.getAddress());
//        System.out.println(emp.getContactPerson());
//        System.out.println(emp.getContactPersonNumber());
//        System.out.println(emp.getContactPersonAddress());
//        System.out.println(emp.getBirthDate());
//        System.out.println(emp.getHireDate());
//        System.out.println(emp.getSchedule());
//        System.out.println(emp.getTime());
//        System.out.println(emp.getPagIbig());
//        System.out.println(emp.getSss());
//        System.out.println(emp.getImageUUID());
//        System.out.println(emp.getEmpStatus());
        emp.setEmpStatus("ACTIVE");

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
                emp.getTime() + "'," +
                emp.getPagIbig() + "," +
                emp.getSss() + ",'" +
                emp.getImageUUID() + "','" +
                emp.getEmpStatus() + "'"
                + ")"
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

    public void updateEmployee(Employee emp) {

        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("update employees set " +
                "last_name = '" + emp.getLastName() + "', " +
                "first_name = '"  + emp.getFirstName() + "', " +
                "middle_name ='" + emp.getMiddleName() + "', " +
                "gender = '" + emp.getGender() + "', " +
                "phone_number = '" + emp.getPhoneNumber() + "', " +
                "address = '" + emp.getAddress() + "', " +
                "contact_person = '" + emp.getContactPerson() + "', " +
                "contact_person_number = '" + emp.getContactPersonNumber() + "', " +
                "contact_person_address = '" + emp.getContactPersonAddress() + "'," +
                "birth_date = '" + emp.getBirthDate() + "'," +
                "hire_date = '" + emp.getHireDate() + "'," +
                "schedule = '" + emp.getSchedule() + "', " +
                "schedule_time = '" + emp.getTime() + "', " +
                "pag_ibig = '" + emp.getPagIbig() + "', " +
                "sss = '" + emp.getSss() + "', " +
                "image_uuid = '" + emp.getImageUUID() + "', " +
                "emp_status = '" + emp.getEmpStatus() + "'" +
                "where pre_empno = " + emp.getPre_empNo() + " AND " +
                "post_empno = " + emp.getPost_empNo());

        try {
            conn.update();
            System.out.println("DATA WRITE COMPLETED");
        } catch (SQLException e) {
            System.out.println("DATA WRITE FAILED");
            e.printStackTrace();
        }
        conn.closeConnection();
    }

    public static void initializeAttendance() {
        for(Employee emp : new EmpReader().getEmpListAttendanceForCurrentDay()) {
            AppConnection conn = new AppConnection();
            conn.loadDriver();
            conn.connectToDB();

            String time = "";

            conn.doSomething("select schedule_time from employees where " +
                    "pre_empno = " + emp.getPre_empNo() + " and " +
                    "post_empno = " + emp.getPost_empNo());

            try {
                conn.query();
                while (conn.getRS().next()) {
                    time = conn.getRS().getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn.doSomething(" insert into attendance " +
                    "(pre_empno, post_empno, workdate, schedule_time)" +
                    "values(" + emp.getPre_empNo() +
                    ", " + emp.getPost_empNo() +
                    ",'" + Strings.getDateFormat().format(new Date()) +
                    "','" + time +"')");

            try {
                conn.update();
            } catch (SQLException e) {
                System.out.println("Record already exists.");
            }
            conn.closeConnection();
        }
    }
}
