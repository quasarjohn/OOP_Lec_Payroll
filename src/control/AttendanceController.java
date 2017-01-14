package control;

import adapters.EmployeeAttendanceListAdapter;
import com.jfoenix.controls.JFXComboBox;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import values.Strings;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class AttendanceController implements Initializable {
    @FXML
    private VBox empListContainer;

    @FXML private FlowPane attendanceListContainer;

    @FXML private JFXComboBox<String> monthCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    void populateEmpList() {

        monthCB.getItems().setAll(Strings.months());

        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            EmployeeAttendanceListAdapter emp = new EmployeeAttendanceListAdapter(attendanceListContainer);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }
    }
}
