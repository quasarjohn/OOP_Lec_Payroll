package control;

import animators.FabAnimator;
import com.jfoenix.controls.JFXButton;
import domain.Domain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import values.Styles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeController implements Initializable {
    @FXML
    private VBox empListContainer, vb;

    @FXML
    private Circle bigProfileImage, fab;

    @FXML
    private StackPane stackpane;

    @FXML
    private Label lbl_name;

    private HBox hb_emp_information, hb_emp_edit_information, hb_add_employee;

    private ImagePattern fabPattern, pattern;

    @FXML
    private JFXButton updateInfoB, generateBadgeB;

    private String activePane = "INFO";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_face4.jpg"));
        pattern = new ImagePattern(image);

        Image fabIcon = new Image(getClass().getResourceAsStream("/resources/images/ic_fab.png"));
        fabPattern = new ImagePattern(fabIcon);
    }

    public void initViews() {
        FXMLLoader emp_information_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Information.fxml"));
        emp_information_loader.setController(this);
        try {
            hb_emp_information = emp_information_loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vb.getChildren().setAll(hb_emp_information);

        bigProfileImage.setFill(pattern);
        fab.setFill(fabPattern);

        FXMLLoader hb_emp_edit_information_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Edit_Information.fxml"));
        hb_emp_edit_information_loader.setController(this);
        try {
            hb_emp_edit_information = hb_emp_edit_information_loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //LOAD ADD EMPLOYEE VIEW
        AddEmployeeController addEmployeeController = new AddEmployeeController(this);
        FXMLLoader hb_add_employee_loader = new FXMLLoader(getClass().
                getResource("/view/Employee_Add_New.fxml"));
        hb_add_employee_loader.setController(addEmployeeController);
        try {
            hb_add_employee = hb_add_employee_loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void populateEmpList() {
        Domain.getEmpList().clear();
        empListContainer.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            EmployeeListFactory emp = new EmployeeListFactory();
            HBox hb = emp.getItem();
            empListContainer.getChildren().addAll(hb);
            Domain.getEmpList().add(hb);
        }
    }

    private boolean editInfoActive = false;

    @FXML
    private void listenToUpdateButton() {



        if (editInfoActive) {
            vb.getChildren().setAll(hb_emp_information);
            editInfoActive = false;
            updateInfoB.setText("UPDATE INFORMATION");
            generateBadgeB.setText("GENERATE BADGE");
            generateBadgeB.setStyle(Styles.ButtonStyles.orangeButton);
            updateInfoB.setStyle(Styles.ButtonStyles.whiteButton);
        } else {
            vb.getChildren().setAll(hb_emp_edit_information);
            editInfoActive = true;
            updateInfoB.setText("SAVE CHANGES");
            generateBadgeB.setText("CANCEL");
            generateBadgeB.setStyle(Styles.ButtonStyles.redButton);
            updateInfoB.setStyle(Styles.ButtonStyles.greenButton);
        }
        //LOAD INFORMATION HERE
        Platform.runLater(() -> {
            bigProfileImage.setFill(pattern);
            fab.setFill(fabPattern);
        });
    }

    @FXML
    private void listenToFAB() {

        activePane = "ADD";

        FabAnimator.hideFab(fab);
        vb.getChildren().setAll(hb_add_employee);

        generateBadgeB.setText("CANCEL");
        generateBadgeB.setStyle(Styles.ButtonStyles.redButton);

        updateInfoB.setText("ADD");
        updateInfoB.setStyle(Styles.ButtonStyles.greenButton);
    }
}
