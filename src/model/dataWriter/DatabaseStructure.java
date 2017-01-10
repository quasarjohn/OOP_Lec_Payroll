package model.dataWriter;

import connection.AppConnection;

/**
 * Created by John on 1/11/2017.
 */
public class DatabaseStructure {

    private static AppConnection conn;

    public static void buildDatabaseStructure() {

        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

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
                                    "schedule_time time, primary key(pre_empNo, post_empNo))");

        conn.execute();
    }
}
