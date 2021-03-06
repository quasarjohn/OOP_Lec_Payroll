package model.dataWriter;

import connection.AppConnection;

/**
 * Created by John on 1/11/2017.
 */
public class DatabaseBuilder {

    private static AppConnection conn;

    public static void buildDatabaseStructure() {

        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        //CREATE TABLE FOR USER ACCOUNT
        conn.doSomething("create table user_account (username varchar(100) " +
                " primary key not null, password varchar(100) not null, role varchar(20) " +
                "not null, date_created date not null, first_name varchar(300) not null, " +
                "last_name varchar(300) not null)");
        conn.execute();

        //CREATE TABLE LOGS
        conn.doSomething(" create table logs(username varchar(100) not null, " +
                "log_date date, log_time time, log_type varchar(30), details varchar(200))");
        conn.execute();

        //CREATE SEQUENCE FOR EMPID
        conn.doSomething("create sequence empNo_seq start with 1 increment by 2 no cycle cache 5");
        conn.execute();

        //CREATE TABLE FOR EMPLOYEE
        conn.doSomething("create table employees(" +
                "pre_empNo integer not null, " +
                "post_empNo integer not null, " +
                "last_name varchar(100) not null, " +
                "first_name varchar(100), " +
                "middle_name varchar(100), " +
                "gender char(1) constraint gender_ck check(gender in ('M', 'F')), " +
                "phone_number varchar(100), " +
                "address varchar(300), " +
                "contact_person varchar(300), " +
                "contact_person_number varchar(100), " +
                "contact_person_address varchar(300), " +
                "birth_date date, " +
                "hire_date date, " +
                "schedule varchar(30), " +
                "schedule_time time, " +
                "pag_ibig decimal(10,2), " +
                "sss decimal(10,2), " +
                "image_uuid varchar(300)," +
                "emp_status varchar(10), " +
                "commission_percentage decimal(10,2), " +
                "hourly_rate decimal(10,2), " +
                "primary key(pre_empNo, post_empNo)" +
                ")");
        conn.execute();

        //CREATE ATTENDANCE TABLE
        conn.doSomething("create table attendance (" +
                "pre_empno integer not null, " +
                "post_empno integer not null, " +
                "workdate date not null, " +
                "schedule_time time, " +
                "timein time, " +
                "timeout time, " +
                "hours_worked time, " +
                "status varchar(10), " +
                "hours_worked_in_sec integer, " +
                "basic_pay decimal(10,2), " +
                "date_paid date, " +
                "primary key(pre_empno, post_empno, workdate), " +
                "foreign key(pre_empno, post_empno) references employees)");
        conn.execute();

        conn.doSomething("create table earnings(pre_empno integer not null, " +
                "post_empno integer not null, workdate date, amount decimal(10,2), " +
                "commission_percentage decimal(10,2), commission decimal(10,2), date_paid date, " +
                "category varchar(50), notes varchar(100), " +
                "foreign key(pre_empno, post_empno, workdate) references attendance )");
        conn.execute();

        conn.doSomething("create table payroll(gendate date not null, pre_empno " +
                "integer not null, post_empno integer not null, total_commission double," +
                " total_basic double, hours_worked double, days_worked integer, pag_ibig decimal(10,2), " +
                "sss decimal(10,2), net_income double, primary key(gendate, pre_empno, post_empno))");
        conn.execute();


        conn.closeConnection();
    }
}
