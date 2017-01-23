package model.dataReader;

import connection.AppConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Created by John on 1/21/2017.
 */
public class YearsReader {

    public static ObservableList<String> getYears() {
        ObservableList<String> years = FXCollections.observableArrayList();
        AppConnection conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();

        conn.doSomething("select distinct year(workdate) from attendance  order by year(workdate) desc");
        try {
            conn.query();
            while (conn.getRS().next()) {
                years.add(conn.getRS().getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }
}
