package driver;

import control.MainController;
import domain.Domain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainController mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setController(mainController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-components.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/resources/css/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        Domain.setPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
