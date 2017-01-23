package model.dataReader;

import connection.AppConnection;
import model.dataStructure.Employee;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by John on 1/14/2017.
 */
public class EmpReader {

    public enum OrderBy {
        LAST_NAME, FIRST_NAME, EMPNO
    }

    public enum ShowFilter {
        ACTIVE, INACTIVE, BOTH
    }

    public static ArrayList<Employee> getEmpList(OrderBy orderBy, ShowFilter showFilter) {

        String order;

        if (orderBy == OrderBy.LAST_NAME) {
            order = "order by last_name";
        } else if (orderBy == OrderBy.FIRST_NAME) {
            order = "order by first_name";
        } else {
            order = "order by pre_empno, post_empno";
        }

        String filter;

        if (showFilter == ShowFilter.ACTIVE) {
            filter = "where emp_status = 'ACTIVE'";
        } else if (showFilter == ShowFilter.INACTIVE) {
            filter = "where emp_status = 'INACTIVE'";
        } else {
            filter = "";
        }

        ArrayList<Employee> employees = new ArrayList<>();

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select pre_empno, post_empno, " +
                "first_name, middle_name, last_name, emp_status from employees " + filter + " " + order);
        try {
            conn.query();
            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setFirstName(conn.getRS().getString(3));
                emp.setMiddleName(conn.getRS().getString(4));
                emp.setLastName(conn.getRS().getString(5));
                emp.setEmpStatus(conn.getRS().getString(6));

                employees.add(emp);
            }
            conn.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static Employee getEmpData(int pre, int post) {

        Employee emp = new Employee();

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

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
                emp.setPagIbig(conn.getRS().getString(16));
                emp.setSss(conn.getRS().getString(17));
                emp.setImageUUID(conn.getRS().getString(18));
                emp.setEmpStatus(conn.getRS().getString(19));
            }
            return emp;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int[] buildPrimaryKey(String key) {
        int[] array = new int[2];

        String pre = key.substring(0, 4);
        String post = key.substring(4);

        array[0] = Integer.parseInt(pre);
        array[1] = Integer.parseInt(post);

        return array;
    }

    public static ArrayList<Employee> getEmpListAttendanceForCurrentDay() {
        ArrayList<Employee> employees = new ArrayList<>();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        conn.doSomething("select e.pre_empno, e.post_empno, e.last_name, " +
                "e.first_name,e.middle_name from employees e where emp_status = 'ACTIVE' and " +
                "schedule like '%" + getCurrentDay() + "%'");
        try {
            conn.query();

            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setLastName(conn.getRS().getString(3));
                emp.setFirstName(conn.getRS().getString(4));
                emp.setMiddleName(conn.getRS().getString(5));

                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    private static String getCurrentDay() {
        String date = new Date().toString();
        String day = date.split(" ", 2)[0];

        if (day.equalsIgnoreCase("Mon"))
            return "M";
        else if (day.equalsIgnoreCase("Tue"))
            return "T";
        else if (day.equalsIgnoreCase("Wed"))
            return "W";
        else if (day.equalsIgnoreCase("Thu"))
            return "TH";
        else if (day.equalsIgnoreCase("Fri"))
            return "F";
        else if (day.equalsIgnoreCase("Sat"))
            return "S";
        else
            return "SU";
    }

    public String[] getAttendanceStatus(Employee emp) {

        System.out.println(emp.getPre_empNo() + " " + emp.getPost_empNo());

        String[] status = new String[3];

        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select e.schedule_time, a1.timein, " +
                "a1.status from employees e left join attendance a on e.pre_empno = a.pre_" +
                "empno AND a.post_empno = e.post_empno left join attendance a1 on a1.pre_empno = " +
                "e.pre_empno and a1.post_empno = e.post_empno " +
                "where e.pre_empno = " + emp.getPre_empNo() + " and e.post_empno = " + emp.getPost_empNo());

        try {
            conn.query();
            while (conn.getRS().next()) {
                status[0] = conn.getRS().getString(1);
                status[1] = conn.getRS().getString(2);
                status[2] = conn.getRS().getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

}
