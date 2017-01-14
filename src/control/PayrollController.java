package control;

import adapters.EmployeePayrollListAdapter;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class PayrollController implements Initializable {
    @FXML
    private VBox empListContainer;

    @FXML
    private ScrollPane sp;
    @FXML
    private ScrollPane sp2;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setFitToWidth(true);
        sp2.setFitToWidth(true);
        sp2.setFitToHeight(true);


    }

    void populateEmpList() {
        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();
        for (int i = 0; i < 5; i++) {
            EmployeePayrollListAdapter emp = new EmployeePayrollListAdapter();
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }
    }
}
