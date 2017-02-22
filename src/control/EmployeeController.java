package control;

import adapters.EmployeeListAdapter;
import animators.CircleAnimator;
import animators.FocusSwapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
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
import values.Strings;
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

    private Employee employee;

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
            contactAddressL, contactNumberL, pagIbigL, sssL, schedL, schedTimeL, empCountL ,
            commissionL;

    private ArrayList<Employee> employees;

    private int position;

    private GenerateIDController generateIDController = new GenerateIDController();

    @FXML
    private JFXComboBox<String> empOrderCB, showSelectionCB;

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
            editEmployeeController.initComponents();
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

    public void initLeftHeader() {
        empOrderCB.getItems().setAll(Strings.orderOptions());
        empOrderCB.getSelectionModel().select(2);

        showSelectionCB.getItems().setAll(Strings.showSelectionOptions());
        showSelectionCB.getSelectionModel().select(0);

        empOrderCB.setOnAction(e -> {
            empListPopulated = false;
            position = 0;
            populateEmpList(0, null);
        });

        showSelectionCB.setOnAction(f -> {
            empListPopulated = false;
            position = 0;
            populateEmpList(0, null);
        });
    }

    private EmpReader.OrderBy orderBy;
    private EmpReader.ShowFilter showFilter;

    public void populateEmpList(int pos, ArrayList<Employee> emps) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                switch (empOrderCB.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        orderBy = EmpReader.OrderBy.FIRST_NAME;
                        break;
                    case 1:
                        orderBy = EmpReader.OrderBy.LAST_NAME;
                        break;
                    case 2:
                        orderBy = EmpReader.OrderBy.EMPNO;
                        break;
                    default:
                        orderBy = EmpReader.OrderBy.EMPNO;
                        break;
                }

                switch (showSelectionCB.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        showFilter = EmpReader.ShowFilter.ACTIVE;
                        break;
                    case 1:
                        showFilter = EmpReader.ShowFilter.INACTIVE;
                        break;
                    case 2:
                        showFilter = EmpReader.ShowFilter.BOTH;
                        break;
                    default:
                        showFilter = EmpReader.ShowFilter.ACTIVE;
                        break;
                }

                if (emps == null) {
                    employees = EmpReader.getEmpList(orderBy, showFilter);
                } else {
                    employees = emps;
                }
                ArrayList<HBox> empList = new ArrayList<>();

                Platform.runLater(() -> {
                    if (empListPopulated == false) {
                        empList.clear();
                        empListContainer.getChildren().clear();

                        if (employees.size() > 0) {
                            showFloatingButtons();
                            for (int i = 0; i < employees.size(); i++) {
                                EmployeeListAdapter emp = new EmployeeListAdapter(i, employees.get(i));
                                HBox hb = emp.getItem();
                                hb.setOnMouseClicked(e -> {
                                    if (e.isStillSincePress()) {
                                        position = emp.getPosition();
                                        FocusSwapper.changeFocus(hb, empList);
                                        loadViewInfoPane();
                                    }
                                });
                                empListContainer.getChildren().addAll(hb);
                                empList.add(hb);
                                if (i == pos && !empListPopulated) {
                                    FocusSwapper.changeFocus(hb, empList);
                                }
                            }
                            empListPopulated = true;
                            empCountL.setText("You have " + empList.size() + " employees.");

                            loadViewInfoPane();

                        } else {
                            updateInfoB.setVisible(false);
                            generateBadgeB.setVisible(false);

                            vb.getChildren().setAll(new EmptyRecord().getEmptyPane());
                        }
                    }
                });
            }
        });

        t.start();

    }

    @FXML
    void listenToUpdateButton() {
        if (activePane == ActivePane.INFO) {
            activePane = ActivePane.UPDATE;
            loadUpdateInfoPane();
            editEmployeeController.initComponents();
            editEmployeeController.bindViews(employee);

            showRestrictionPane();

            Domain.getToolbarTitle().setText("UPDATE INFORMATION");

        } else if (activePane == ActivePane.UPDATE) {
            if (editEmployeeController.updateEmployee()) {
                activePane = ActivePane.INFO;

                empListPopulated = false;
                populateEmpList(position, null);
                hideRestrictionPane();
                Domain.getToolbarTitle().setText("EMPLOYEES");
            }
        } else if (activePane == ActivePane.ADD) {
            if (addEmployeeController.addEmployee()) {
                employees = EmpReader.getEmpList(orderBy, showFilter);

                activePane = ActivePane.INFO;
                empListPopulated = false;
                populateEmpList(position, null);
                Platform.runLater(() -> CircleAnimator.showFab(fab));
                hideRestrictionPane();
            }
        }
    }

    @FXML
    private void listenToGenerateBadgeB() {
        if (activePane == ActivePane.INFO) {
            generateIDController.generateID(employee);

        } else if (activePane == ActivePane.UPDATE) {
            activePane = ActivePane.INFO;
            loadViewInfoPane();

            hideRestrictionPane();

            Domain.getToolbarTitle().setText("EMPLOYEES");
        } else if (activePane == ActivePane.ADD) {
            activePane = ActivePane.INFO;
            CircleAnimator.showFab(fab);
            try {
                loadViewInfoPane();
            } catch (Exception e) {
                System.out.println("There are no employees.");
            }
            hideRestrictionPane();

            Domain.getToolbarTitle().setText("EMPLOYEES");
        }
    }

    @FXML
    private void listenToFAB() {
        updateInfoB.setVisible(true);
        generateBadgeB.setVisible(true);

        Platform.runLater(() -> CircleAnimator.hideFab(fab));

        showRestrictionPane();

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

    public void loadViewInfoPane() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    vb.getChildren().setAll(hb_emp_information);
                    updateInfoB.setText("UPDATE INFORMATION");
                    generateBadgeB.setText("GENERATE BADGE");
                    generateBadgeB.setStyle(Styles.ButtonStyles.orangeButton);
                    updateInfoB.setStyle(Styles.ButtonStyles.whiteButton);

                    try {
                        employee = EmpReader.getEmpData(employees.get(position).getPre_empNo(),
                                employees.get(position).getPost_empNo());

                        lbl_name.setText(employee.getFirstName() + " " +
                                employee.getMiddleName().charAt(0) + ". " + employee.getLastName());
                        phoneNumberL.setText("Phone Number:    " + employee.getPhoneNumber());
                        addressL.setText("Address:    " + employee.getAddress());
                        hireDateL.setText("Hire Date: " + employee.getHireDate());
                        birthDayL.setText("Birth Date: " + employee.getBirthDate());
                        contactPersonL.setText("Contact Person: " + employee.getContactPerson());
                        contactAddressL.setText("Address: " + employee.getContactPersonAddress());
                        contactNumberL.setText("Phone Number: " + employee.getContactPersonNumber());
                        pagIbigL.setText("Pag-ibig: " + employee.getPagIbig());
                        sssL.setText("SSS: " + employee.getSss());
                        schedL.setText("Days: " + employee.getSchedule().trim().replace(" ", ", "));
                        schedTimeL.setText("Time: " + employee.getTime());
                        commissionL.setText("COMMISSION: " + employee.getCommission() + "%");

                        bigProfileImage.setFill(Images.getImagePatternFromFile(this, ResourcePaths.dpPath +
                                employee.getImageUUID()));
                    } catch (Exception e) {
                        vb.getChildren().setAll(new EmptyRecord().getEmptyPane());
                        generateBadgeB.setVisible(false);
                        updateInfoB.setVisible(false);
                    }
                });
            }
        });

        t.start();

    }

    private void loadUpdateInfoPane() {
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    vb.getChildren().setAll(hb_emp_edit_information);
                    updateInfoB.setText("SAVE CHANGES");
                    generateBadgeB.setText("CANCEL");
                    generateBadgeB.setStyle(Styles.ButtonStyles.redButton);
                    updateInfoB.setStyle(Styles.ButtonStyles.greenButton);
                });
            }
        });

        t.start();
    }

    private void showRestrictionPane() {
        VBox vb = (VBox) empListSP.getChildren().get(3);
        vb.setVisible(true);
        CircleAnimator.showRestrictionPane(vb);
    }

    private void hideRestrictionPane() {
        VBox vb = (VBox) empListSP.getChildren().get(3);
        CircleAnimator.hideRestrictionPane(vb);
    }

    private void hideFlotaingButtons() {
        generateBadgeB.setVisible(false);
        updateInfoB.setVisible(false);
    }

    private void showFloatingButtons() {
        generateBadgeB.setVisible(true);
        updateInfoB.setVisible(true);
    }
}