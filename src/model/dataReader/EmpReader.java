package model.dataReader;

import connection.AppConnection;
import model.dataStructure.Employee;
import values.Strings;

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
                "first_name, middle_name, last_name, emp_status, image_uuid from employees " + filter + " " + order);
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
                emp.setImageUUID(conn.getRS().getString(7));

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
                emp.setCommission(conn.getRS().getString(20));
                emp.setHourlyRate(conn.getRS().getString(21));
            }
            return emp;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Employee>
    getEmpListAttendanceForSpecificDay(String date, AttendanceShowSelection selection, AttendanceOrderSelection orderSelection) {
        ArrayList<Employee> employees = new ArrayList<>();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        String activeFilter;
        String orderFilter;

        switch (selection) {
            case ACTIVE_W_SCHED:
                activeFilter = "where e.emp_status = 'ACTIVE' and " +
                        "e.schedule like '%" + getCurrentDay() + "%'  AND a.workdate = '" + date + "'";
                break;
            case ACTIVE:
                activeFilter = "where e.emp_status = 'ACTIVE' ";
                break;
            case BOTH:
                activeFilter = " where e.emp_status = 'ACTIVE' OR e.emp_status = 'INACTIVE'";
                break;
            case INACTIVE:
                activeFilter = "where e.emp_status = 'INACTIVE' ";
                break;
            default:
                activeFilter = "where e.emp_status = 'ACTIVE' and " +
                        "e.schedule like '%" + getCurrentDay() + "%' ";
                break;
        }

        switch (orderSelection) {
            case EMPNO:
                orderFilter = " order by e.pre_empno, e.post_empno";
                break;
            case FIRSTNAME:
                orderFilter = " order by e.last_name";
                break;
            case LASTNAME:
                orderFilter = " order by e.first_name";
                break;
            default:
                orderFilter = " order by e.pre_empno, e.post_empno";
                break;
        }

        System.out.println(activeFilter);

        conn.doSomething("select e.pre_empno, e.post_empno, e.last_name, " +
                "e.first_name,e.middle_name, a.timein, a.schedule_time, a.status, e.image_uuid " +
                "from employees e left join attendance a on e.pre_empno = a.pre_empno " +
                "AND a.post_empno = e.post_empno " +
                activeFilter + " " + orderFilter);
        try {
            conn.query();

            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setLastName(conn.getRS().getString(3));
                emp.setFirstName(conn.getRS().getString(4));
                emp.setMiddleName(conn.getRS().getString(5));
                emp.setTimein(conn.getRS().getString(6));
                emp.setTime(conn.getRS().getString(7));
                emp.setStatus(conn.getRS().getString(8));
                emp.setImageUUID(conn.getRS().getString(9));

                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public enum AttendanceShowSelection {
        ACTIVE_W_SCHED, ACTIVE, INACTIVE, BOTH;
    }

    public enum AttendanceOrderSelection {
        EMPNO, LASTNAME, FIRSTNAME
    }

    public static ArrayList<Employee> getEmpListAttendanceForCurrentDay() {
        ArrayList<Employee> employees = new ArrayList<>();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select e.pre_empno, e.post_empno, e.last_name, " +
                "e.first_name,e.middle_name, a.timein, a.schedule_time, a.status " +
                "from employees e left join attendance a on e.pre_empno = a.pre_empno " +
                "AND a.post_empno = e.post_empno " +
                "where e.emp_status = 'ACTIVE' and " +
                "e.schedule like '%" + getCurrentDay() + "%'");
        try {
            conn.query();

            while (conn.getRS().next()) {
                Employee emp = new Employee();
                emp.setPre_empNo(conn.getRS().getInt(1));
                emp.setPost_empNo(conn.getRS().getInt(2));
                emp.setLastName(conn.getRS().getString(3));
                emp.setFirstName(conn.getRS().getString(4));
                emp.setMiddleName(conn.getRS().getString(5));
                emp.setTimein(conn.getRS().getString(6));
                emp.setTime(conn.getRS().getString(7));
                emp.setStatus(conn.getRS().getString(8));

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

    public static double getRatePerSec(Employee employee) {
        double rate = 0;
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();
        conn.doSomething("select hourly_rate from employees where pre_empno = " +
                employee.getPre_empNo() + " AND post_empno  =" + employee.getPost_empNo());
        try {
            conn.query();
            while (conn.getRS().next()) {
                rate = conn.getRS().getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rate / 3600;
    }
}
