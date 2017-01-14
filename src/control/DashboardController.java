package control;

import adapters.EmployeeDashboardListAdapter;
import animators.CircleAnimator;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import values.Styles;

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

    @FXML
    private StackPane stackPane;

    @FXML
    private Button dashboardAddB, cancelB;

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
            EmployeeDashboardListAdapter emp = new EmployeeDashboardListAdapter(jobsDoneListContainer);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }

        dashboardAddB.setStyle(Styles.ButtonStyles.greenButton);
        cancelB.setStyle(Styles.ButtonStyles.redButton);
    }

    @FXML
    private void listenToFAB() {
        CircleAnimator.hideFab(fab);
        //fab.setVisible(false);
        stackPane.getChildren().get(0).toFront();
    }

    @FXML private void listenToCancel() {
        CircleAnimator.showFab(fab);
        stackPane.getChildren().get(0).toFront();
    }
}
