package values;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by John on 1/14/2017.
 */
public class Images {

    public static ImagePattern getImagePattern(Object context, String path) {
        Image image = new Image(context.getClass().getResourceAsStream(path));
        return new ImagePattern(image);
    }

    public static ImagePattern getImagePatternFromFile(Object context, String path)  {
        Image image = null;
        try {
            image = new Image(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            image = new Image(context.getClass().getResourceAsStream(ResourcePaths.camPath));
        }

        if(image == null) {
            image = new Image(context.getClass().getResourceAsStream(ResourcePaths.camPath));
        }

        return new ImagePattern(image);
    }
}
