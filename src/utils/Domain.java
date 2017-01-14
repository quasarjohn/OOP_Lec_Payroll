package utils;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by John on 12/10/2016.
 */
public class Domain {

    private static Label toolbarTitle;
    private static Stage primaryStage;
    private static Scene scene;
    private static BorderPane borderPane;
    private static ArrayList<HBox> empList = new ArrayList<>();

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Domain.primaryStage = primaryStage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        Domain.scene = scene;
    }

    public static BorderPane getBorderPane() {
        return borderPane;
    }

    public static void setBorderPane(BorderPane borderPane) {
        Domain.borderPane = borderPane;
    }

    public static ArrayList<HBox> getEmpList() {
        return empList;
    }

    public static void setEmpList(ArrayList<HBox> empList) {
        Domain.empList = empList;
    }

    public static Label getToolbarTitle() {
        return toolbarTitle;
    }

    public static void setToolbarTitle(Label toolbarTitle) {
        Domain.toolbarTitle = toolbarTitle;
    }
}
