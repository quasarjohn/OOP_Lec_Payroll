package control;

import com.jfoenix.controls.JFXButton;
import com.sun.org.apache.xml.internal.security.Init;
import domain.Domain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by John on 12/10/2016.
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton loginB;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginB.getStyleClass().add("button-raised");
        loginB.setOnAction(e -> listenToLogin());
    }

    private void listenToLogin() {

        HomeController homeController = new HomeController();
        FXMLLoader homePaneLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
        homePaneLoader.setController(homeController);
        try {
            Stage stage = Domain.getPrimaryStage();
            stage.hide();
            Parent root = homePaneLoader.load();
            Scene scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-components.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/resources/css/home.css").toExternalForm());
            stage.setScene(scene);
            homeController.listenToItem1();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
