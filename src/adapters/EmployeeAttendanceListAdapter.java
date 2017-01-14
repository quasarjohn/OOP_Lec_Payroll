package adapters;

import utils.Domain;
import animators.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeAttendanceListAdapter {

    @FXML
    private Circle circle;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;

    private int i = 0;

    private HBox hBox;
    public EmployeeAttendanceListAdapter(FlowPane attendanceListContainer) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee_Dashboard.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_face4.jpg"));
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);

        hBox.setOnMouseClicked(e -> {
            i = 0;
            if (e.isStillSincePress()) {
                FocusSwapper.changeFocus(hBox, Domain.getEmpList());
                attendanceListContainer.getChildren().clear();
                for (; i < 31; i++) {
                    MonthToDateAttendanceListAdapter mtd = new MonthToDateAttendanceListAdapter();
                    VBox hb = mtd.getItem();
                    attendanceListContainer.getChildren().addAll(hb);
                }
            }
        });
    }

    public HBox getItem() {
        hBox.setStyle("-fx-background-color:white;");
        title.setStyle("-fx-font-size: 16px");
        subtitle.setStyle("-fx-font-size: 12px");
        return hBox;
    }
}
