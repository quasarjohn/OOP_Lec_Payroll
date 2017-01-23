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

    private String month, year;

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
        Image image = new Image(getClass().getResourceAsStream("/resources/images/ic_face4.jpg"));
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);

        title.setText(emp.getFirstName() + " " + emp.getMiddleName().charAt(0) + ". " +
        emp.getLastName());

        String[] status = new EmpReader().getAttendanceStatus(emp);

        if(status[0] == null) {
            status[0] = "";
        }

        if(status[1] == null) {
            status[1] = "";
        }

        if(status[2] == null) {
            status[2] = "";
        }

        subtitle.setText("Schedule: " + status[0] +
                "   |   Time-in: " + status[1]);

        statusL.setText(status[2]);
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

    public void loadTable(String month, String year) {
        //TODO LOAD TABLE FOR ATTENDANCE

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
