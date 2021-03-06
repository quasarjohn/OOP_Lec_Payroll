package driver;

import control.LoginController;
import javafx.scene.image.Image;
import model.dataWriter.EmployeeWriter;
import utils.Domain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.getIcons().add(new Image("/resources/images/ic_kitty.png"));

        EmployeeWriter.initializeAttendance();

        System.out.println("DONE");

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.hide();
        LoginController mainController = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setController(mainController);
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-components.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/resources/css/login.css").toExternalForm());
        primaryStage.setScene(scene);

        Domain.setPrimaryStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
