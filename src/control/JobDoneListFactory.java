package control;

import domain.Domain;
import domain.FocusSwapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class JobDoneListFactory {

    private int index;
    private HBox hBox;
    public JobDoneListFactory(int index) {
        this.index = index;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/List_Item_Job_Done.fxml"));
        loader.setController(this);
        try {
            hBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hBox.setOnMouseClicked(e -> {
            if(e.isStillSincePress()) {
                //FocusSwapper.changeFocus(hBox, Domain.getEmpList());
            }
        });
    }



    public HBox getItem() {
        if(index % 2 == 0) {
            hBox.setStyle("-fx-border-color: gray;-fx-border-width:0 0 0 0 ;");
        }
        else {
            hBox.setStyle("-fx-border-color: gray;-fx-border-width:0 0 0 0 ; -fx-background-color: #ebebe0");
        }

        return hBox;
    }
}
