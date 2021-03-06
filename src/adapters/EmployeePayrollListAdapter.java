package adapters;


import model.dataStructure.Employee;
import utils.Domain;
import animators.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import values.Images;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeePayrollListAdapter {

    @FXML
    private Circle circle;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;

    private int position = 0;
    private Employee employee;

    private HBox hBox;
    public EmployeePayrollListAdapter(int position, Employee employee) {
        this.position = position;
        this.employee = employee;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee_Payroll.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_face4.jpg"));
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);

        hBox.setOnMouseClicked(e -> {
            if (e.isStillSincePress()) {
                FocusSwapper.changeFocus(hBox, Domain.getEmpList());
            }
        });

        bindData();
    }


    private void bindData() {

        title.setText(employee.getFirstName() + " " + employee.getMiddleName().charAt(0) + ". " +
                employee.getLastName());
        subtitle.setText(employee.getPre_empNo() + "" + employee.getPost_empNo());
        circle.setFill(Images.getImagePatternFromFile(this,"C:KFAVImages/" + employee.getImageUUID()));
    }

    public HBox getItem() {
        hBox.setStyle("-fx-background-color:white;");
        title.setStyle("-fx-font-size: 16px");
        subtitle.setStyle("-fx-font-size: 12px");
        return hBox;
    }

    public int getPosition() {
        return position;
    }
}
