package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by John on 1/14/2017.
 */
public class EmptyRecord {

    public HBox getEmptyPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmptyRecord.fxml"));
        try {
            return (HBox)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
