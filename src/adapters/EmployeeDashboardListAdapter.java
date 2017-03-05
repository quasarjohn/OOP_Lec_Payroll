package adapters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.dataReader.DashboardReader;
import model.dataStructure.Employee;
import utils.DateUtils;
import utils.Domain;
import animators.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import values.Images;
import values.Strings;

import java.io.IOException;
import java.util.Date;

/**
 * Created by John on 12/11/2016.
 */
public class EmployeeDashboardListAdapter {

    private VBox jobsDoneListContainer;
    @FXML
    private Circle circle;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;

    private int position;

    private TableView table;

    private HBox hBox;
    private Employee emp;
    private String date;
    public EmployeeDashboardListAdapter(VBox jobsDoneListContainer, Employee emp,
                                        int position, TableView table, String date) {
        this.date = date;
        this.emp  = emp;
        this.table = table;
        this.position = position;
        this.jobsDoneListContainer = jobsDoneListContainer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Employee_Dashboard.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setText(emp.getFirstName() + " " + emp.getMiddleName().charAt(0) + ". " +
        emp.getLastName());
        subtitle.setText("Time-in: " + emp.getTimein() +" | Hours worked: " +
                DateUtils.getHoursWorked(DateUtils.timeToInt(emp.getTimein())));
        circle.setFill(Images.getImagePatternFromFile(this,"C:KFAVImages/" + emp.getImageUUID()));
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

    public void loadTableContents() {
        table.getItems().clear();
        table.getColumns().clear();
        //LOAD TABLE HEADERS
        for(int i = 0; i < 5; i++) {
            final int j = i;

            TableColumn col = new TableColumn(Strings.dashboardIndividialHeader().get(i));
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            table.getColumns().addAll(col);
            table.setItems(DashboardReader.getIndividualDashboardData(emp.getPre_empNo(), emp.getPost_empNo(),
                    date));
        }
    }
}
