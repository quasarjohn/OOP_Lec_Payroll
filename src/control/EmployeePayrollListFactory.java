package control;


import domain.Domain;
import domain.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeePayrollListFactory {

    @FXML
    private Circle circle;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;

    private HBox hBox;
    public EmployeePayrollListFactory() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee_Payroll.fxml"));
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
            if (e.isStillSincePress()) {
                FocusSwapper.changeFocus(hBox, Domain.getEmpList());
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
