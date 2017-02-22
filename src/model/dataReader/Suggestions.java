package model.dataReader;

import connection.AppConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by John on 1/28/2017.
 */
public class Suggestions {

    private AppConnection conn;

    public Suggestions() {
        conn = new AppConnection();
        conn.loadDriver();
        conn.connectToDB();
    }

    public ObservableList<String> getCategorySuggestions(String str) {
        ObservableList<String> suggestions = FXCollections.observableArrayList();
        conn.doSomething("select distinct category from earnings where category like UPPER('%" + str + "%')");
        try {
            conn.query();
            while (conn.getRS().next()) {
                suggestions.add(conn.getRS().getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestions;
    }
}
