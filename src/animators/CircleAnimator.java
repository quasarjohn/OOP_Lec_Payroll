package animators;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Created by John on 1/8/2017.
 */
public class CircleAnimator {

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

    public static void showDP(Circle circle) {
        Platform.runLater(() -> {
            circle.setRadius(0);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), ae -> {
                circle.setRadius(circle.getRadius() + 14.6);

            }));
            timeline.setCycleCount(5);
            timeline.play();
        });
    }

    public static void showRestrictionPane(VBox vbox) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), vbox);

                    st.setFromX(0);
                    st.setFromY(0);

                    st.setToX(10);
                    st.setToY(10);

                    st.play();
                });
            }
        });
        t.start();
    }

    public static void hideRestrictionPane(VBox vbox) {
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), vbox);

                    st.setFromX(1);
                    st.setFromY(1);

                    st.setToX(0);
                    st.setToY(0);

                    st.play();
                });
            }
        });

        t.start();
    }
}
