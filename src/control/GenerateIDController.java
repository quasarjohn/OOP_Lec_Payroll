package control;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.dataStructure.Employee;
import values.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by John on 1/15/2017.
 */
public class GenerateIDController {

    private Stage stage;
    private HBox hb = new HBox();

    public GenerateIDController() {
        stage = new Stage();
        hb = new HBox();

        hb.setSpacing(1);
        hb.setId("idHB");


        Scene scene = new Scene(hb);
        scene.getStylesheets().add(getClass().getResource("/resources/css/id.css").toExternalForm());
        stage.setScene(scene);

        FXMLLoader frontLoader = new FXMLLoader(getClass().getResource("/view/GenID.fxml"));
        frontLoader.setController(this);

        BorderPane front = null;

        try {
            front = frontLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader backLoader = new FXMLLoader(getClass().getResource("/view/GenIDBack.fxml"));
        backLoader.setController(this);

        VBox back = null;

        try {
            back = backLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hb.getChildren().addAll(front);
        hb.getChildren().addAll(back);
    }

    @FXML
    private Label nameL, contactNumberL, emerygencyContactL, emergencyNumberL;
    @FXML
    private Text addressL;

    @FXML
    private Rectangle idPic;

    @FXML
    private VBox qrContainer;

    public void generateID(Employee employee) {
        nameL.setText(employee.getLastName() + ", " +
        employee.getFirstName() + " " +
        employee.getMiddleName().charAt(0) + ".");

        contactNumberL.setText(employee.getPhoneNumber());
        emerygencyContactL.setText(employee.getContactPerson());
        addressL.setText(employee.getContactPersonAddress());
        emergencyNumberL.setText(employee.getContactPersonNumber());

        idPic.setFill(Images.getImagePatternFromFile(getClass(), "C:KFAVImages/" + employee.getImageUUID()));

        stage.centerOnScreen();
        stage.show();

        qrContainer.getChildren().setAll(getQRCode(employee.getPre_empNo() + "" + employee.getPost_empNo()));

        generateSnapshot(hb, employee);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ImageView getQRCode(String empNo) {

        String charSet = "UTF-8";
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode
                    (new String(empNo.getBytes(charSet), charSet),
                            BarcodeFormat.QR_CODE, 150, 150, hintMap);
        } catch (UnsupportedEncodingException | WriterException e) {
            e.printStackTrace();
        }

        return bfToImage(MatrixToImageWriter.toBufferedImage(matrix));
    }

    private ImageView bfToImage(BufferedImage image) {
        WritableImage wr = null;
        if(image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for(int x = 0; x  < image.getWidth(); x++) {
                for(int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr);
    }

    public void generateSnapshot(HBox hb, Employee emp) {
        WritableImage  image = hb.snapshot(new SnapshotParameters(), null);
        File file = new File("C:KFAVIDs/" + emp.getLastName()+ "ID.png");
        file.getParentFile().mkdirs();
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
