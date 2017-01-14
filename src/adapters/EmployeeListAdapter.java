package adapters;

import model.dataStructure.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeListAdapter {

    @FXML
    private Label title;
    @FXML
    private Label subtitle;

    private HBox hBox;
    private int position = 0;
    private Employee employee;
    public EmployeeListAdapter(int position, Employee employee) {
        this.position = position;
        this.employee = employee;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bindData();
    }

    private void bindData() {
        title.setText(employee.getFirstName() + " " + employee.getMiddleName().charAt(0) + ". " +
        employee.getLastName());

        subtitle.setText(employee.getPre_empNo() + "" + employee.getPost_empNo());
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
