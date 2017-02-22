package control;

import adapters.EmployeeAttendanceListAdapter;
import animators.FocusSwapper;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import model.dataReader.EmpReader;
import model.dataReader.YearsReader;
import model.dataStructure.Employee;
import utils.DateUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import values.Strings;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @FXML
    private JFXComboBox empOrderCB, showSelectionCB;
    @FXML
    private JFXDatePicker attendaceDatePicker;

    @FXML
    private Label attSinceHiredL, mtdAttendanceL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean componentsInit = false;
    private EmpReader.AttendanceShowSelection selection;
    private EmpReader.AttendanceOrderSelection orderBy;

    @FXML
    void populateEmpList() {

        if (!componentsInit) {
            initComponents();
            initLeftHeader();
            componentsInit = true;
        }

        String date = attendaceDatePicker.getValue().getYear() + "-" +
                attendaceDatePicker.getValue().getMonthValue() + "-" +
                attendaceDatePicker.getValue().getDayOfMonth();

        switch (showSelectionCB.getSelectionModel().getSelectedIndex()) {
            case 0:
                selection = EmpReader.AttendanceShowSelection.ACTIVE_W_SCHED;
                break;
            case 1:
                selection = EmpReader.AttendanceShowSelection.ACTIVE;
                break;
            case 2:
                selection = EmpReader.AttendanceShowSelection.INACTIVE;
                break;
            case 3:
                selection = EmpReader.AttendanceShowSelection.BOTH;
                break;
            default:
                selection = EmpReader.AttendanceShowSelection.ACTIVE_W_SCHED;
                break;
        }

        switch (empOrderCB.getSelectionModel().getSelectedIndex()) {
            case 0:
                orderBy = EmpReader.AttendanceOrderSelection.FIRSTNAME;
                break;
            case 1:
                orderBy = EmpReader.AttendanceOrderSelection.LASTNAME;
                break;
            case 2:
                orderBy = EmpReader.AttendanceOrderSelection.EMPNO;
                break;
            default:
                break;
        }

        ArrayList<Employee> employees = EmpReader.getEmpListAttendanceForSpecificDay(date, selection, orderBy);
        empListContainer.getChildren().clear();

        ArrayList<HBox> items = new ArrayList<>();

        if (employees.size() == 0) {
            table.getItems().clear();
        }

        for (int i = 0; i < employees.size(); i++) {

            EmployeeAttendanceListAdapter emp = new EmployeeAttendanceListAdapter(table, employees.get(i), i);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            items.add(hb);
            hb.setOnMouseClicked(e -> {
                System.out.println(yearCB.getSelectionModel().getSelectedItem());
                FocusSwapper.changeFocus(hb, items);
                emp.loadTable(Integer.toString(monthCB.getSelectionModel().getSelectedIndex() + 1),
                        yearCB.getSelectionModel().getSelectedItem(), attSinceHiredL, mtdAttendanceL);
            });

            if (i == 0) {
                FocusSwapper.changeFocus(items.get(0), items);
                emp.loadTable(Integer.toString(monthCB.getSelectionModel().getSelectedIndex() + 1),
                        yearCB.getSelectionModel().getSelectedItem(), attSinceHiredL, mtdAttendanceL);
            }
        }
    }

    private void initComponents() {
        yearCB.getItems().setAll(YearsReader.getYears());
        yearCB.getSelectionModel().select(0);
        monthCB.getItems().setAll(Strings.months());
        monthCB.getSelectionModel().select(DateUtils.getCurrentMonth() - 1);
    }

    public void initLeftHeader() {
        empOrderCB.getItems().setAll(Strings.orderOptions());
        empOrderCB.getSelectionModel().select(2);

        int[] date = DateUtils.dateToInt(Strings.getDateFormat().format(new Date()));
        attendaceDatePicker.setValue(LocalDate.of(date[0], date[1], date[2]));

        attendaceDatePicker.setOnAction(e -> {
            populateEmpList();
        });

        showSelectionCB.getItems().setAll(Strings.showAttendanceSelectionOptions());
        showSelectionCB.getSelectionModel().select(0);

        empOrderCB.setOnAction(e -> {
            populateEmpList();
        });
    }

    @FXML
    public void reloadAttendanceList() {
        System.out.println("XXXX");
        if (showSelectionCB.getSelectionModel().getSelectedIndex() != 0) {
            attendaceDatePicker.setVisible(false);
        } else {
            attendaceDatePicker.setVisible(true);
        }

        populateEmpList();
    }
}
