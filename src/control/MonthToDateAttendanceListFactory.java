package control;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class MonthToDateAttendanceListFactory {

    private VBox vb;

    public MonthToDateAttendanceListFactory() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Attendance.fxml"));
        loader.setController(this);
        try {
            vb = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vb.setOnMouseClicked(e -> {
            if (e.isStillSincePress()) {
                //FocusSwapper.changeFocus(hBox, Domain.getEmpList());
            }
        });
    }

    public VBox getItem() {
        vb.setStyle("-fx-border-color: gray;-fx-border-width:0 0 0 0 ; -fx-background-color: #ebebe0");
        return vb;
    }
}
