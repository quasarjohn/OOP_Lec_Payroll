package control;

import adapters.EmployeeAttendanceListAdapter;
import animators.FocusSwapper;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.dataReader.EmpReader;
import model.dataReader.YearsReader;
import model.dataStructure.Employee;
import utils.DatePickerUtils;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import values.Strings;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class AttendanceController implements Initializable {
    @FXML
    private VBox empListContainer;

    @FXML
    private FlowPane attendanceListContainer;

    @FXML
    private JFXComboBox<String> monthCB, yearCB;

    @FXML
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void populateEmpList() {
        initComponents();
        initLeftHeader();

        ArrayList<Employee> employees = EmpReader.getEmpListAttendanceForCurrentDay();
        empListContainer.getChildren().clear();

        ArrayList<HBox> items = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {

            EmployeeAttendanceListAdapter emp = new EmployeeAttendanceListAdapter(table, employees.get(i), i);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            items.add(hb);
            hb.setOnMouseClicked(e -> {
                System.out.println(yearCB.getSelectionModel().getSelectedItem());
                FocusSwapper.changeFocus(hb, items);
                emp.loadTable(Integer.toString(monthCB.getSelectionModel().getSelectedIndex() + 1),
                        yearCB.getSelectionModel().getSelectedItem());
            });
        }
    }

    private void initComponents() {
        yearCB.getItems().setAll(YearsReader.getYears());
        yearCB.getSelectionModel().select(0);
        monthCB.getItems().setAll(Strings.months());
        monthCB.getSelectionModel().select(DatePickerUtils.getCurrentMonth() - 1);
    }

    @FXML private JFXComboBox empOrderCB, showSelectionCB;
    public void initLeftHeader() {
        empOrderCB.getItems().setAll(Strings.orderOptions());
        empOrderCB.getSelectionModel().select(2);

        showSelectionCB.getItems().setAll(Strings.showSelectionOptions());
        showSelectionCB.getSelectionModel().select(0);

        empOrderCB.setOnAction(e -> {

        });

        showSelectionCB.setOnAction(f -> {

        });
    }
}
