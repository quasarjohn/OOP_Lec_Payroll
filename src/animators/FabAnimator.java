package animators;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Created by John on 1/8/2017.
 */
public class FabAnimator {

    public static void hideFab(Circle circle) {

        Platform.runLater(() -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), ae -> {
                circle.setRadius(circle.getRadius() - 5);

            }));
            timeline.setCycleCount(6);
            timeline.play();
        });
    }

    public static void showFab(Circle circle) {
        Platform.runLater(() -> {
            circle.setRadius(0);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), ae -> {
                circle.setRadius(circle.getRadius() + 5.4);

            }));
            timeline.setCycleCount(5);
            timeline.play();
        });

    }
}
