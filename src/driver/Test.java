package driver;

import connection.AppConnection;
import model.dataReader.EmpReader;
import model.dataStructure.Attendance;
import model.dataWriter.DatabaseStructure;
import values.Strings;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by John on 1/11/2017.
 */
public class Test {

    public static void main(String[] args) {

        DatabaseStructure.buildDatabaseStructure();

//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat time = new SimpleDateFormat("hh.mm.ss");
//
//        Attendance attendance = new Attendance();
//        attendance.setPre_empno(1701);
//        attendance.setPost_empno(111);
//        attendance.setTimeSched("10.00.00");
//        attendance.setStatus("ON-TIME");
//        attendance.setWorkDate(date.format(new Date()));
//        attendance.setTimeIn(time.format(new Date()));
//
//        AppConnection conn = new AppConnection();
//        conn.loadDriver();
//        conn.connectToDB();
//        conn.doSomething("insert into attendance values(" +
//                attendance.getPre_empno() + "," +
//                attendance.getPost_empno() + ",'" +
//                attendance.getWorkDate() + "','" +
//                attendance.getTimeSched() + "','" +
//                attendance.getTimeIn() + "','" +
//                attendance.getStatus() +
//                "')");
//        try {
//            conn.update();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

}
