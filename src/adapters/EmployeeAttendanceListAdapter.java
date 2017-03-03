package adapters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.dataReader.AttendanceReader;
import model.dataReader.EmpReader;
import model.dataStructure.Attendance;
import model.dataStructure.Employee;
import utils.Domain;
import animators.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import values.Images;
import values.Strings;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeAttendanceListAdapter {

    @FXML
    private Circle circle;
    @FXML
    private Label title, subtitle, statusL;

    private HBox hBox;

    private int position;

    private TableView table;

    private Employee emp;

    public EmployeeAttendanceListAdapter(TableView table, Employee emp, int position) {
        this.position = position;
        this.table = table;
        this.emp = emp;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee_Attendance.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        circle.setFill(Images.getImagePatternFromFile(this,"C:KFAVImages/" + emp.getImageUUID()));

        title.setText(emp.getFirstName() + " " + emp.getMiddleName().charAt(0) + ". " +
        emp.getLastName());

        subtitle.setText("Schedule: " + emp.getTime() +
                "   |   Time-in: " + emp.getTimein());

        statusL.setText(emp.getStatus());
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

    public void loadTable(String month, String year, Label attSinceHiredL, Label mtdAttendanceL) {

        attSinceHiredL.setText(AttendanceReader.getAttendancePercentage(emp) + "%");
        mtdAttendanceL.setText(AttendanceReader.getAttendancePercentage(emp, month, year) + "%");

        table.getItems().clear();
        table.getColumns().clear();
        //LOAD TABLE HEADERS
        for(int i = 0; i < 6; i++) {
            final int j = i;

            TableColumn col = new TableColumn(Strings.attendanceTableHeader().get(i));
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setCellValueFactory(new Callback <TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            table.getColumns().addAll(col);
        }

        table.setItems(new AttendanceReader().getEmpAttendance(emp, month, year));
    }
}
