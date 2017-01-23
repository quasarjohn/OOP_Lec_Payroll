package control;

import com.jfoenix.controls.JFXButton;
import utils.Domain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import animators.ScreenController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by John on 12/10/2016.
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton loginB;

    @FXML private BorderPane loginPane;

    @FXML private HBox header;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginB.getStyleClass().add("button-raised");
        loginB.setOnAction(e -> listenToLogin());

        Platform.runLater(() -> {
            loadScreenControls();
        });

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

    @FXML
    private void closeApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        Optional result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void minimizeApp() {
        Domain.getPrimaryStage().setIconified(true);
    }

    private void loadScreenControls() {
        ScreenController controller =  new ScreenController(loginPane, Domain.getPrimaryStage(), header);
    }
}
