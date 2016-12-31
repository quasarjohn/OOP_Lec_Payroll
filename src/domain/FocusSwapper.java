package domain;

import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * Created by John on 12/12/2016.
 */
public class FocusSwapper {

    public static void changeFocus(HBox hb, ArrayList<HBox> items) {
        resetDrawerItemFocus(items);
        focusItem(hb);
    }

    private static void resetDrawerItemFocus(ArrayList<HBox> items) {
        for(HBox item : items) {
            item.setStyle("-fx-background-color:white; -fx-border-color: white; -fx-border-color: gray;-fx-border-width:0 0 0.5 0;");
        }
    }

    private static void focusItem(HBox hBox) {
        hBox.setStyle("-fx-background-color:#8cd98c; -fx-border-color: #8cd98c; -fx-border-color: gray;-fx-border-width:0 0 0 0 ;" );
    }

}
