package control;

import adapters.EmployeePayrollListAdapter;
import animators.FocusSwapper;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;
import model.dataReader.EmpReader;
import model.dataReader.PayrollReader;
import model.dataStructure.Employee;
import model.dataWriter.ActiveUser;
import model.dataWriter.Logger;
import model.dataWriter.PayrollWriter;
import utils.DateUtils;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    private HBox payslipContainer;

    @FXML
    private ComboBox<String> paydatesCB;

    private ArrayList<Employee> employees;

    private int position = 0;

    private ReportGenerator reportGenerator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setFitToWidth(true);
        sp2.setFitToWidth(true);
        sp2.setFitToHeight(true);
    }

    void populateEmpList() {
        reportGenerator = new ReportGenerator();
        payslipContainer.setHgrow(reportGenerator, Priority.ALWAYS);

        paydatesCB.getItems().setAll(PayrollReader.getPaydates());
        paydatesCB.getSelectionModel().select(0);

        ArrayList<HBox> items = new ArrayList<>();

        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();

        employees = EmpReader.getEmpList(EmpReader.OrderBy.LAST_NAME, EmpReader.ShowFilter.ACTIVE);

        for (int i = 0; i < employees.size(); i++) {
            EmployeePayrollListAdapter emp = new EmployeePayrollListAdapter(i, employees.get(i));
            HBox hb = emp.getItem();
            items.add(hb);

            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);

            hb.setOnMouseClicked(e -> {
                FocusSwapper.changeFocus(hb, items);
                position = emp.getPosition();
                System.out.println(position);
                generateReport();
            });

            if (i == position) {
                Platform.runLater(() -> {
                    FocusSwapper.changeFocus(items.get(0), items);
                });
            }
        }
        Platform.runLater(() -> generateReport());
    }

    @FXML
    void listenToGeneratePayrollB() {
        new PayrollWriter().generate(employees);
        paydatesCB.getItems().setAll(PayrollReader.getPaydates());
        Logger.log(Logger.LogType.PAY_GENERATE, ActiveUser.getUsername(),
                "Generated payslip for " + DateUtils.getCurrentDate());
    }

    private void generateReport() {
        payslipContainer.getChildren().setAll(reportGenerator);
        reportGenerator.generateIndivPayroll(employees.get(position), paydatesCB.getSelectionModel().getSelectedItem());
    }
}
