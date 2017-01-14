package adapters;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by John on 12/11/2016.
 */
public class JobDoneListAdapter {

    private int index;
    private HBox hBox;
    public JobDoneListAdapter(int index) {
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
