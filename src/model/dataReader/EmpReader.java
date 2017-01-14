package model.dataReader;

import connection.AppConnection;
import model.dataStructure.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by John on 1/14/2017.
 */
public class EmpReader {

    public static ArrayList<Employee> getEmpList() {
        ArrayList<Employee> employees = new ArrayList<>();

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select pre_empno, post_empno, " +
                "first_name, middle_name, last_name from employees");
        try {
            conn.query();
            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setFirstName(conn.getRS().getString(3));
                emp.setMiddleName(conn.getRS().getString(4));
                emp.setLastName(conn.getRS().getString(5));

                employees.add(emp);
            }
            conn.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static Employee getEmpData(int pre, int post) {
        String key = pre + "" + post;

        Employee emp = new Employee();

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        //TODO GET EMP DATA
        conn.doSomething("select *from employees where pre_empno = " + pre + " AND " +
                "post_empno = " + post + "");
        try {
            conn.query();
            while (conn.getRS().next()) {
                emp.setPre_empNo(pre);
                emp.setPost_empNo(post);
                emp.setLastName(conn.getRS().getString(3));
                emp.setFirstName(conn.getRS().getString(4));
                emp.setMiddleName(conn.getRS().getString(5));
                emp.setGender(conn.getRS().getString(6));
                emp.setPhoneNumber(conn.getRS().getString(7));
                emp.setAddress(conn.getRS().getString(8));
                emp.setContactPerson(conn.getRS().getString(9));
                emp.setContactPersonNumber(conn.getRS().getString(10));
                emp.setContactPersonAddress(conn.getRS().getString(11));
                emp.setBirthDate(conn.getRS().getString(12));
                emp.setHireDate(conn.getRS().getString(13));
                emp.setSchedule(conn.getRS().getString(14));
                emp.setTime(conn.getRS().getString(15));
                emp.setPagIbig(conn.getRS().getDouble(16));
                emp.setSss(conn.getRS().getDouble(17));
                emp.setImageUUID(conn.getRS().getString(18));
            }
            return emp;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public static int[] buildPrimaryKey(String key) {
        int[] array = new int[2];

        String pre =  key.substring(0, 4);
        String post = key.substring(4);

        array[0] = Integer.parseInt(pre);
        array[1] = Integer.parseInt(post);

        return array;
    }
}
