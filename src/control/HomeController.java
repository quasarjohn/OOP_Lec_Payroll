package control;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import domain.Domain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import screenControls.ScreenController;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by John on 12/10/2016.
 */
public class HomeController implements Initializable {

    @FXML
    private Label toolbarTitle;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private BorderPane borderpane, body;

    private FXMLLoader leftDrawerLoader;

    public HomeController() {
        inflateDrawer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDrawer();
        loadListeners();
        inflatePanes();

        header.setFitToWidth(true);
        header.setFitToHeight(true);

        loadScreenControls();

    }

    private void inflateDrawer() {
        leftDrawerLoader = new FXMLLoader(getClass().getResource("/view/LeftDrawer.fxml"));
        leftDrawerLoader.setController(this);
    }

    @FXML
    private HBox drawerItem1;
    @FXML
    private HBox drawerItem2;
    @FXML
    private HBox drawerItem3;
    @FXML
    private HBox drawerItem4;

    @FXML private HBox headerContent;

    @FXML
    private ScrollPane header;
    private ArrayList<HBox> drawerItems = new ArrayList<>();

    private void loadDrawer() {
        try {
            ScrollPane leftDrawer = leftDrawerLoader.load();

            //leftDrawer.setFitToHeight(true);
            drawer.setSidePane(leftDrawer);
        } catch (IOException e) {
        }

        drawerItems.add(drawerItem1);
        drawerItems.add(drawerItem2);
        drawerItems.add(drawerItem3);
        drawerItems.add(drawerItem4);
    }

    public void listenToItem1() {
        toolbarTitle.setText("DASHBOARD");
        resetDrawerItemFocus();
        focusItem(drawerItem1);
        body.setCenter(dashboardPane);
        Platform.runLater(() -> {
            dashboardController.populateEmpList();
            Domain.getPrimaryStage().show();

        });
    }

    @FXML
    public void listenToItem2() {
        toolbarTitle.setText("PAYROLL");
        resetDrawerItemFocus();
        focusItem(drawerItem2);
        Platform.runLater(() -> {
            payrollController.populateEmpList();
            body.setCenter(payrollPane);
        });
    }

    @FXML
    public void listenToItem3() {
        toolbarTitle.setText("EMPLOYEES");
        resetDrawerItemFocus();
        focusItem(drawerItem3);

        Platform.runLater(() -> {
            body.setCenter(empPane);
            employeeController.populateEmpList();
            employeeController.initViews();
        });
    }

    @FXML
    public void listenToItem4() {
        toolbarTitle.setText("ATTENDANCE");
        resetDrawerItemFocus();
        focusItem(drawerItem4);
        Platform.runLater(() -> {
            attendanceController.populateEmpList();
            body.setCenter(attendancePane);
        });
    }

    private void resetDrawerItemFocus() {
        for (HBox item : drawerItems) {
            item.setStyle("-fx-background-color:white");
        }
    }

    private void focusItem(HBox hBox) {
        hBox.setStyle("-fx-background-color:#8cd98c");
    }

    private void loadListeners() {
        hamburger.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                if (drawer.isShown()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        });

        borderpane.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                if (drawer.isShown()) {
                    drawer.close();
                }
            });
        });
    }

    private DashboardController dashboardController;
    private PayrollController payrollController;
    private EmployeeController employeeController;
    private AttendanceController attendanceController;

    private HBox dashboardPane, payrollPane, empPane, settingsPane, attendancePane;

    private void inflatePanes() {
        //Inflate Layouts
        FXMLLoader dashboardPaneLoader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
        FXMLLoader payrollPaneLoader = new FXMLLoader(getClass().getResource("/view/Payroll.fxml"));
        FXMLLoader empPaneLoader = new FXMLLoader(getClass().getResource("/view/Employees.fxml"));
        FXMLLoader settingsPaneLoader = new FXMLLoader(getClass().getResource("/view/Settings.fxml"));
        FXMLLoader attendancePaneLoader = new FXMLLoader(getClass().getResource("/view/Attendance.fxml"));

        //Initialize Controllers
        dashboardController = new DashboardController();
        payrollController = new PayrollController();
        employeeController = new EmployeeController();
        attendanceController = new AttendanceController();

        //Set controllers
        dashboardPaneLoader.setController(dashboardController);
        payrollPaneLoader.setController(payrollController);
        empPaneLoader.setController(employeeController);
        attendancePaneLoader.setController(attendanceController);

        //Load views
        try {
            dashboardPane = dashboardPaneLoader.load();
            payrollPane = payrollPaneLoader.load();
            empPane = empPaneLoader.load();
            settingsPane = settingsPaneLoader.load();
            attendancePane = attendancePaneLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ImageView powerB;
    private void loadScreenControls() {
        ScreenController controls = new ScreenController(borderpane, Domain.getPrimaryStage(), headerContent);
    }

    @FXML
    private void closeApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        Optional result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void minimizeApp() {
        Domain.getPrimaryStage().setIconified(true);
    }
}
