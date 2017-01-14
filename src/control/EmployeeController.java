package control;

import adapters.EmployeeListAdapter;
import animators.CircleAnimator;
import animators.FocusSwapper;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import model.dataReader.EmpReader;
import model.dataStructure.Employee;
import utils.Domain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import utils.EmptyRecord;
import values.Images;
import values.ResourcePaths;
import values.Styles;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeController implements Initializable {

    private AddEmployeeController addEmployeeController;

    private EditEmployeeController editEmployeeController;

    @FXML
    private VBox empListContainer, vb;

    @FXML
    private Circle bigProfileImage, fab;

    @FXML
    private StackPane stackpane, empListSP;


    private HBox hb_emp_information, hb_emp_edit_information, hb_add_employee;
    @FXML
    private JFXButton updateInfoB, generateBadgeB;

    private ActivePane activePane = ActivePane.INFO;

    //WIRE VIEWS FOR EMPLOYEE INFORMATION MODULE
    @FXML
    private Label lbl_name, phoneNumberL, addressL, hireDateL, birthDayL, contactPersonL,
            contactAddressL, contactNumberL, pagIbigL, sssL, schedL, schedTimeL;

    private ArrayList<Employee> employees;

    private int position;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initViews() {
        fab.setFill(Images.getImagePattern(this, ResourcePaths.fabPath));
        FXMLLoader emp_information_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Information.fxml"));
        emp_information_loader.setController(this);
        try {
            hb_emp_information = emp_information_loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vb.getChildren().setAll(hb_emp_information);


        editEmployeeController = new EditEmployeeController(this);
        FXMLLoader hb_emp_edit_information_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Edit_Information.fxml"));
        hb_emp_edit_information_loader.setController(editEmployeeController);
        try {
            hb_emp_edit_information = hb_emp_edit_information_loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //LOAD ADD EMPLOYEE VIEW
        addEmployeeController = new AddEmployeeController(this);
        FXMLLoader hb_add_employee_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Add_New.fxml"));
        hb_add_employee_loader.setController(addEmployeeController);
        try {
            hb_add_employee = hb_add_employee_loader.load();
            addEmployeeController.initComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean empListPopulated = false;

    void populateEmpList() {
        if (empListPopulated == false) {
            Domain.getEmpList().clear();
            empListContainer.getChildren().clear();
            employees = EmpReader.getEmpList();

            if (employees.size() > 0) {
                for (int i = 0; i < employees.size(); i++) {
                    EmployeeListAdapter emp = new EmployeeListAdapter(i, employees.get(i));
                    HBox hb = emp.getItem();
                    hb.setOnMouseClicked(e -> {
                        if (e.isStillSincePress()) {
                            position = emp.getPosition();
                            FocusSwapper.changeFocus(hb, Domain.getEmpList());
                            loadViewInfoPane();
                        }
                    });
                    empListContainer.getChildren().addAll(hb);
                    Domain.getEmpList().add(hb);

                    if (i == 0 && !empListPopulated) {
                        FocusSwapper.changeFocus(hb, Domain.getEmpList());
                    }
                }
                empListPopulated = true;
                Platform.runLater(() -> loadViewInfoPane());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("You don't have any employees");
                alert.show();
                updateInfoB.setVisible(false);
                generateBadgeB.setVisible(false);

                vb.getChildren().setAll(new EmptyRecord().getEmptyPane());
            }
        }
    }

    @FXML
    private void listenToUpdateButton() {
        if (activePane == ActivePane.INFO) {
            //TODO LOAD UPDATE PANE
            activePane = ActivePane.UPDATE;
            loadUpdateInfoPane();
            empListSP.getChildren().get(0).toFront();
            Domain.getToolbarTitle().setText("UPDATE INFORMATION");

        } else if (activePane == ActivePane.UPDATE) {
            //TODO UPDATE EMPLOYEE and GO BACK TO INFO PANE
            activePane = ActivePane.INFO;
            loadViewInfoPane();
            empListSP.getChildren().get(3).toBack();
            Domain.getToolbarTitle().setText("EMPLOYEES");
        } else if (activePane == ActivePane.ADD) {
            //TODO ADD EMPLOYEE and GO BACK TO INFO PANE
//            Thread t = new Thread(() -> addEmployeeController.addEmployee());
//            t.start();

            addEmployeeController.addEmployee();

            employees = EmpReader.getEmpList();

            activePane = ActivePane.INFO;
            empListSP.getChildren().get(3).toBack();
            Domain.getToolbarTitle().setText("EMPLOYEES");
            Platform.runLater(() -> CircleAnimator.showFab(fab));
            loadViewInfoPane();
            empListPopulated = false;
            populateEmpList();
        }
    }

    @FXML
    private void listenToGenerateBadgeB() {
        if (activePane == ActivePane.INFO) {
            //TODO GENERATE BADGE
        } else if (activePane == ActivePane.UPDATE) {
            //TODO CANCEL UPDATE and GO BACK TO INFO PANE
            activePane = ActivePane.INFO;
            loadViewInfoPane();
            empListSP.getChildren().get(3).toBack();
            Domain.getToolbarTitle().setText("EMPLOYEES");
        } else if (activePane == ActivePane.ADD) {
            //TODO CANCEL ADD and GO BACK TO INFO PANE
            activePane = ActivePane.INFO;
            CircleAnimator.showFab(fab);
            try {
                loadViewInfoPane();
            } catch (Exception e) {
                System.out.println("There are no employees.");
            }
            empListSP.getChildren().get(3).toBack();
            Domain.getToolbarTitle().setText("EMPLOYEES");
        }
    }

    @FXML
    private void listenToFAB() {
        updateInfoB.setVisible(true);
        generateBadgeB.setVisible(true);

        Platform.runLater(() -> CircleAnimator.hideFab(fab));

        empListSP.getChildren().get(0).toFront();
        activePane = ActivePane.ADD;
        Domain.getToolbarTitle().setText("ADD EMPLOYEE");

        //LOAD ADD EMPLOYEE PANE
        vb.getChildren().setAll(hb_add_employee);

        generateBadgeB.setText("CANCEL");
        generateBadgeB.setStyle(Styles.ButtonStyles.redButton);

        updateInfoB.setText("ADD");
        updateInfoB.setStyle(Styles.ButtonStyles.greenButton);
    }

    private enum ActivePane {
        INFO, UPDATE, ADD
    }

    private void loadViewInfoPane() {
        vb.getChildren().setAll(hb_emp_information);
        updateInfoB.setText("UPDATE INFORMATION");
        generateBadgeB.setText("GENERATE BADGE");
        generateBadgeB.setStyle(Styles.ButtonStyles.orangeButton);
        updateInfoB.setStyle(Styles.ButtonStyles.whiteButton);

        Employee employee = EmpReader.getEmpData(employees.get(position).getPre_empNo(),
                employees.get(position).getPost_empNo());

        System.out.println(employee.getAddress());

//        @FXML Label phoneNumberL, addressL, hireDateL, birthDayL, contactPersonL,
//                contactAddressL, contactNumberL;

        lbl_name.setText(employee.getFirstName() + " " +
                employee.getMiddleName().charAt(0) + ". " + employee.getLastName());
        phoneNumberL.setText("Phone Number:    " + employee.getPhoneNumber());
        addressL.setText("Address:    " + employee.getAddress());
        hireDateL.setText("Hire Date: " + employee.getHireDate());
        birthDayL.setText("Birth Date: " + employee.getBirthDate());
        contactPersonL.setText("Contact Person: " + employee.getContactPerson());
        contactAddressL.setText("Address: " + employee.getContactPersonAddress());
        contactNumberL.setText("Phone Number: " + employee.getContactPersonAddress());
        pagIbigL.setText("Pag-ibig: " + employee.getPagIbig());
        sssL.setText("SSS: " + employee.getSss());
        schedL.setText("Days: " + employee.getSchedule().trim().replace(" ", ", "));
        schedTimeL.setText("Time: " + employee.getTime());

        bigProfileImage.setFill(Images.getImagePatternFromFile(this, ResourcePaths.dpPath + employee.getImageUUID()));

    }

    private void loadUpdateInfoPane() {
        vb.getChildren().setAll(hb_emp_edit_information);
        updateInfoB.setText("SAVE CHANGES");
        generateBadgeB.setText("CANCEL");
        generateBadgeB.setStyle(Styles.ButtonStyles.redButton);
        updateInfoB.setStyle(Styles.ButtonStyles.greenButton);
    }
}