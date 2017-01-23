package animators;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by John on 1/9/2017.
 */
public class ScreenController {

    private BorderPane borderPane;
    private Stage primaryStage;
    private HBox header;
    private double xOffset;
    private double yOffset;
    private double initHeight;
    private double initWidth;
    private double initX;
    private double initY;
    private double initMouseX;
    private double initMouseY;
    private boolean isFullScreen = false;
    private double previousX;
    private double previousY;
    private double difference = 0.0D;
    double mousePos = 0.0D;
    private double initTAWidth;
    private double initTAHeight;
    double taDifference;

    public ScreenController(BorderPane borderPane, Stage stage, HBox header) {
        this.borderPane = borderPane;
        this.primaryStage = stage;this.header = header;
        this.setListeners();
    }

    public void resizeScreen(MouseEvent e) {
        if(this.initMouseX < 5.0D) {
            this.difference = this.initX - e.getScreenX();
            this.primaryStage.setWidth(this.initWidth + this.difference);
            this.moveScreen(e, "x");
        }

        if(this.initMouseX >= this.initWidth - 5.0D) {
            this.difference = this.initX - e.getScreenX();
            this.primaryStage.setWidth(this.initWidth - this.difference);
        }

        if(this.initMouseY < 5.0D) {
            this.difference = this.initY - e.getScreenY();
            this.primaryStage.setHeight(this.initHeight + this.difference);
            this.moveScreen(e, "y");
        }

        if(this.initMouseY >= this.initHeight - 5.0D) {
            this.difference = this.initY - e.getScreenY();
            this.primaryStage.setHeight(this.initHeight - this.difference);
        }

    }

    public void moveScreen(MouseEvent e, String direction) {
        if(direction.equals("x")) {
            this.primaryStage.setX(e.getScreenX() + this.xOffset);
        } else if(direction.equals("y")) {
            this.primaryStage.setY(e.getScreenY() + this.yOffset);
        } else {
            this.primaryStage.setX(e.getScreenX() + this.xOffset);
            this.primaryStage.setY(e.getScreenY() + this.yOffset);
        }

    }

    public void minimize() {
        this.primaryStage.setIconified(true);
    }

    public void setCursor(MouseEvent e) {
    }

    public void maximize() {
        if(this.isFullScreen) {
            this.primaryStage.setFullScreen(false);
            this.primaryStage.setX(this.previousX);
            this.primaryStage.setY(this.previousY);
            this.isFullScreen = false;
        } else {
            this.previousX = this.primaryStage.getX();
            this.previousY = this.primaryStage.getY();
            this.isFullScreen = true;
            this.primaryStage.setFullScreen(true);
            this.primaryStage.setX(0.0D);
            this.primaryStage.setY(0.0D);
        }

    }

    public void dblClickMaximize(MouseEvent e) {
        if(e.getClickCount() == 2) {
            this.maximize();
        }
    }

    public void setListeners() {
        this.borderPane.setOnMousePressed((e) -> {
            this.xOffset = this.primaryStage.getX() - e.getScreenX();
            this.yOffset = this.primaryStage.getY() - e.getScreenY();
            this.initHeight = this.primaryStage.getHeight();
            this.initWidth = this.primaryStage.getWidth();
            this.initX = e.getScreenX();
            this.initY = e.getScreenY();
            this.initMouseX = e.getX();
            this.initMouseY = e.getY();
        });
        this.header.setOnMouseDragged((e) -> {
            if(!this.isFullScreen) {
                this.moveScreen(e, "");
            }

        });
        this.borderPane.setOnMouseDragged((e) -> {
            if(!this.isFullScreen) {
                this.resizeScreen(e);
            }

        });

        this.header.setOnMouseClicked((e) -> {
            if(e.getClickCount() == 2) {
                this.maximize();
            }
        });

    }
}
