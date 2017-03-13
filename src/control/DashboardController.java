package control;

import adapters.EmployeeDashboardListAdapter;
import adapters.JobDoneListAdapter;
import animators.CircleAnimator;
import animators.FocusSwapper;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import model.dataReader.DashboardReader;
import model.dataReader.EmpReader;
import model.dataReader.Suggestions;
import model.dataStructure.Earning;
import model.dataStructure.Employee;
import model.dataWriter.DashboardWriter;
import utils.DateUtils;
import utils.Domain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import utils.FilterUtils;
import values.Strings;
import values.Styles;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
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

    private int position = 0;

    private Suggestions suggestions;

    private ArrayList<Employee> employees;

    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox empOrderCB;

    @FXML
    private Label salonIncomeL, salonCommissionL, netSalonIncomeL,
            empCommissionL, empBasicPayL, empTotalEarning, salonBasicPayL;

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setFitToWidth(true);
        sp2.setFitToWidth(true);
        sp2.setFitToHeight(true);

        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_fab.png"));
        ImagePattern pattern = new ImagePattern(image);
        fab.setFill(pattern);

        suggestions = new Suggestions();

        categoryCB.getJFXEditor().setOnKeyTyped(e -> {

            categoryCB.getItems().setAll(suggestions.getCategorySuggestions(categoryCB.getJFXEditor().getText().toUpperCase()));
            categoryCB.show();
        });

        categoryCB.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.TAB) {
                Platform.runLater(() -> {
                    categoryCB.hide();
                    priceCB.requestFocus();
                });
            }
        });

        empOrderCB.getItems().setAll(Strings.orderOptions());
        empOrderCB.getSelectionModel().select(0);

        int[] date = DateUtils.dateToInt(Strings.getDateFormat().format(new Date()));
        datePicker.setValue(LocalDate.of(date[0], date[1], date[2]));

        FilterUtils.restrictToDecimal(priceCB.getJFXEditor());
    }

    private String date;

    void populateEmpList() {
        table.getItems().clear();
        date = DateUtils.getDateFromDatePicker(datePicker);

        System.out.println(date);

        employees = DashboardReader.getEmpList(date);

        if (employees.size() > 0) {
            CircleAnimator.showFab(fab);
        } else CircleAnimator.hideFab(fab);

        ArrayList<HBox> items = new ArrayList<>();

        empListContainer.getChildren().clear();
        for (int i = 0; i < employees.size(); i++) {
            EmployeeDashboardListAdapter emp = new EmployeeDashboardListAdapter(jobsDoneListContainer,
                    employees.get(i), i, table, date, datePicker);
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            items.add(hb);

            hb.setOnMouseClicked(e -> {
                if (e.isStillSincePress()) {
                    FocusSwapper.changeFocus(hb, items);
                    position = emp.getPosition();
                    emp.loadTableContents();
                    loadDashboardHeaderData();
                    System.out.println(position);
                }
            });

            if (i == position) {
                emp.loadTableContents();
                FocusSwapper.changeFocus(hb, items);
            }
        }

        loadDashboardHeaderData();
        dashboardAddB.setStyle(Styles.ButtonStyles.greenButton);
        cancelB.setStyle(Styles.ButtonStyles.redButton);
    }

    @FXML
    private void listenToFAB() {
        CircleAnimator.hideFab(fab);
        //fab.setVisible(false);
        stackPane.getChildren().get(0).toFront();
        Platform.runLater(() -> categoryCB.requestFocus());
    }

    @FXML
    private void listenToCancel() {
        CircleAnimator.showFab(fab);
        stackPane.getChildren().get(0).toFront();
    }

    @FXML
    private JFXComboBox<String> categoryCB, priceCB;
    @FXML
    private TextArea notesTA;
    @FXML
    private TableView table;

    @FXML
    void listenToAdd() {
        Earning earning = new Earning();
        earning.setPre_empno(employees.get(position).getPre_empNo() + "");
        earning.setPost_empno(employees.get(position).getPost_empNo() + "");
        earning.setWorkdate(Strings.getDateFormat().format(new Date()));
        earning.setAmount(Double.parseDouble(priceCB.getJFXEditor().getText()));
        earning.setCommissioPercentage(Double.parseDouble(employees.get(position).getCommission()));
        earning.setCommission((Double.parseDouble(priceCB.getJFXEditor().getText()) *
                Double.parseDouble(employees.get(position).getCommission())) / 100);
        earning.setDate_paid(null);
        earning.setCategory(categoryCB.getJFXEditor().getText());
        earning.setNotes(notesTA.getText());

        DashboardWriter.addWorkDone(earning);
        stackPane.getChildren().get(0).toFront();

        populateEmpList();
        loadTableContents();

        refreshFields();
    }

    public void loadTableContents() {
        table.getItems().clear();
        table.getColumns().clear();
        //LOAD TABLE HEADERS
        for (int i = 0; i < 5; i++) {
            final int j = i;

            TableColumn col = new TableColumn(Strings.dashboardIndividialHeader().get(i));
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            table.getColumns().addAll(col);
            table.setItems(DashboardReader.getIndividualDashboardData(employees.get(position).getPre_empNo(),
                    employees.get(position).getPost_empNo(), date));
        }
    }

    @FXML
    private void listenToDatePicker() {
        System.out.println(datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-" +
                datePicker.getValue().getDayOfYear());

        System.out.println(new SimpleDateFormat("yyyy-M-dd").format(new Date()));

//        if (isDatesEqual(datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-" +
//                datePicker.getValue().getDayOfYear(), new SimpleDateFormat("yyyy-M-dd").format(new Date())))
//            CircleAnimator.showFab(fab);
//        else
//            CircleAnimator.hideFab(fab);

        populateEmpList();
    }

    @FXML
    private void listenToOrderBy() {

    }

    private boolean isDatesEqual(String date, String date1) {
        String[] parts = date.split("-");
        String[] parts1 = date1.split("-");

        if (parts[0].equals(parts1[0]) && parts[1].equals(parts1[1]) && parts[2].equals(parts1[2]))
            return true;
        else return false;
    }

    private void resetDashboardHeader() {
        salonIncomeL.setText("Salon Income: 0");
        salonCommissionL.setText("-Commission: 0");

        netSalonIncomeL.setText("0");
        salonBasicPayL.setText("-Basic Pay: 0");

        empCommissionL.setText("Total Commission: 0");
        empBasicPayL.setText("Total Basic Pay: 0");
        empTotalEarning.setText("Total Earning: 0");
    }

    private void loadDashboardHeaderData() {
        if (employees.size() > 0) {
            double data[] = new DashboardReader().getTotalEarningAndCommission(DateUtils.getDateFromDatePicker(datePicker));
            double empTotalBasicPay = 0;

            empTotalBasicPay = Double.parseDouble(new
                    DashboardReader().getTotalEmpHour(employees.get(position), DateUtils.getDateFromDatePicker(datePicker)));

            salonIncomeL.setText("Salon Income: " + df.format(data[0]));
            salonCommissionL.setText("-Commission: " + df.format(data[1]));

            double totalBasicPay = new DashboardReader().getTotalBasicPay(employees, datePicker);

            netSalonIncomeL.setText(df.format(data[0] - (data[1] + totalBasicPay)));
            salonBasicPayL.setText("-Basic Pay: " + df.format(totalBasicPay));

            for (Employee e : employees) {
                System.out.println(e.getPost_empNo());
            }

            int pre = employees.get(position).getPre_empNo();
            int post = employees.get(position).getPost_empNo();

            double empTotalCommission = DashboardReader.getTotalCommission(
                    pre, post, DateUtils.getDateFromDatePicker(datePicker));
            empCommissionL.setText("Total Commission: " + df.format(empTotalCommission));
            empBasicPayL.setText("Total Basic Pay: " + df.format(empTotalBasicPay));
            empTotalEarning.setText("Total Earning: " + df.format(empTotalCommission + empTotalBasicPay));
        } else {
            resetDashboardHeader();
        }
    }

    private void refreshFields() {
        notesTA.setText("");
        categoryCB.getJFXEditor().setText("");
        priceCB.getJFXEditor().setText("");
    }
}
