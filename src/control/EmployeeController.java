package control;

import domain.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeController implements Initializable {
    @FXML
    private VBox empListContainer;

    @FXML private Circle bigProfileImage;

    @FXML private Circle fab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_face4.jpg"));
        ImagePattern pattern = new ImagePattern(image);
        bigProfileImage.setFill(pattern);

        Image fabIcon = new Image(getClass().getResourceAsStream("/resources/images/ic_fab.png"));
        ImagePattern fabPattern = new ImagePattern(fabIcon);
        fab.setFill(fabPattern);
    }

    void populateEmpList() {
        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            EmployeeListFactory emp = new EmployeeListFactory();
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }
    }
}
