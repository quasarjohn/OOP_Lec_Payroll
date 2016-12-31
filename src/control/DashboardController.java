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
public class DashboardController implements Initializable {
    @FXML
    private VBox empListContainer;
    @FXML
    private VBox jobsDoneListContainer;

    @FXML
    private ScrollPane sp;
    @FXML
    private ScrollPane sp2;

    @FXML
    private Circle fab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setFitToWidth(true);
        sp2.setFitToWidth(true);
        sp2.setFitToHeight(true);

        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_fab.png"));
        ImagePattern pattern = new ImagePattern(image);
        fab.setFill(pattern);
    }

    void populateEmpList() {
        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            EmployeeDashboardListFactory emp = new EmployeeDashboardListFactory(jobsDoneListContainer);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }
    }
}
