package control;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import model.dataStructure.Employee;
import model.dataWriter.EmployeeWriter;
import utils.DatePickerUtils;
import utils.Domain;
import utils.FileUtils;
import values.Images;
import values.ResourcePaths;
import values.Strings;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Created by John on 1/11/2017.
 */
public class EditEmployeeController {

    //WIRE VIEWS
    @FXML
    private JFXTextField tf_firstName, tf_middleName, tf_lastName, tf_phoneNumber, tf_address,

    tf_contactPerson, tf_contactAddress, tf_contactPersonNumber,

    tf_pagIbig, tf_sss;

    @FXML
    private Circle bigProfileImage;

    @FXML
    private JFXCheckBox m, t, w, th, f, s, su;

    @FXML
    JFXDatePicker hireDatePicker, birthdayPicker, scheduleTimePicker;

    @FXML
    private JFXRadioButton female, male;

    private File dpPath;

    private EmployeeController context;

    public EditEmployeeController(EmployeeController context) {
        this.context = context;
    }

    public void initComponents() {
        scheduleTimePicker.setShowTime(true);
        scheduleTimePicker.setTime(LocalTime.of(10, 00));

        hireDatePicker.setValue(LocalDate.now());

        bigProfileImage.setFill(Images.getImagePattern(this, ResourcePaths.camPath));

        bigProfileImage.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(Domain.getPrimaryStage());

            if(file != null) {
                File dir = null;

                try {
                    dir = new File(ResourcePaths.dpPath);
                    dir.mkdir();
                } catch (Exception e1) {

                }

                dpPath = file;
                bigProfileImage.setFill(Images.getImagePatternFromFile(this, file.getPath()));

            }

            System.out.println(file.getPath());
        });
    }

    public void addEmployee() {
        String uuid = UUID.randomUUID().toString();

        Employee emp = new Employee();
        emp.setPre_empNo(Integer.parseInt(Strings.preDate()));
        emp.setLastName(tf_lastName.getText());
        emp.setFirstName(tf_firstName.getText());
        emp.setMiddleName(tf_middleName.getText());

        if (m.isSelected()) {
            emp.setGender("M");
        } else {
            emp.setGender("F");
        }

        emp.setPhoneNumber(tf_phoneNumber.getText());
        emp.setAddress(tf_address.getText());
        emp.setContactPerson(tf_contactPerson.getText());
        emp.setContactPersonNumber(tf_contactPersonNumber.getText());
        emp.setContactPersonAddress(tf_contactAddress.getText());
        emp.setBirthDate(birthdayPicker.getValue().getYear() + "-" +
                DatePickerUtils.wordToInt(birthdayPicker.getValue().getMonth().toString())
                + "-" + birthdayPicker.getValue().getDayOfMonth());
        emp.setHireDate(hireDatePicker.getValue().getYear() + "-" +
                DatePickerUtils.wordToInt(hireDatePicker.getValue().getMonth().toString())
                + "-" + hireDatePicker.getValue().getDayOfMonth());
        emp.setSchedule(buildSchedule());

        emp.setTime(scheduleTimePicker.getTime().toString());

        emp.setPagIbig(Double.parseDouble(tf_pagIbig.getText()));
        emp.setSss(Double.parseDouble(tf_sss.getText()));

        emp.setImageUUID(uuid);


        EmployeeWriter.addEmployee(emp);

        try {
            FileUtils.copyFile(dpPath, new File(ResourcePaths.dpPath + uuid));
        } catch (IOException e1) {
            System.out.println("Directory already exists.");
        }
    }

    private String buildSchedule() {
        String schedule = "";

        if (m.isSelected()) schedule += " M";

        if (t.isSelected()) schedule += " T";

        if (w.isSelected()) schedule += " W";

        if (th.isSelected()) schedule += " TH";

        if (f.isSelected()) schedule += " F";

        if (s.isSelected()) schedule += " S";

        if (su.isSelected()) schedule += " SU";

        return schedule;
    }
}
