package control;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import model.dataStructure.Employee;
import model.dataWriter.ActiveUser;
import model.dataWriter.EmployeeWriter;
import model.dataWriter.Logger;
import utils.*;
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
public class AddEmployeeController {

    //WIRE VIEWS
    @FXML
    private JFXTextField tf_firstName, tf_middleName, tf_lastName, tf_phoneNumber, tf_address,

    tf_contactPerson, tf_contactAddress, tf_contactPersonNumber,

    tf_pagIbig, tf_sss, tf_commission, tf_ratePerHour;

    @FXML
    private Circle bigProfileImage;

    @FXML
    private JFXCheckBox m, t, w, th, f, s, su;

    @FXML
    JFXDatePicker hireDatePicker, birthdayPicker, scheduleTimePicker;

    @FXML
    private JFXRadioButton female, male;

    private File dpPath, file;


    private EmployeeController context;

    public AddEmployeeController(EmployeeController context) {
        this.context = context;
    }

    public void initComponents() {
        scheduleTimePicker.setShowTime(true);
        scheduleTimePicker.setTime(LocalTime.of(10, 00));

        FilterUtils.restrictToDecimal(tf_pagIbig);
        FilterUtils.restrictToDecimal(tf_sss);
        FilterUtils.restrictToDecimal(tf_commission);
        FilterUtils.restrictToDecimal(tf_ratePerHour);

        FilterUtils.restrictToNumbers(tf_phoneNumber);
        FilterUtils.restrictToNumbers(tf_contactPersonNumber);

        hireDatePicker.setValue(LocalDate.now());

        bigProfileImage.setFill(Images.getImagePattern(this, ResourcePaths.camPath));

        bigProfileImage.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg"));

            file = fc.showOpenDialog(Domain.getPrimaryStage());

            if (file != null) {
                File dir = null;

                try {
                    dir = new File(ResourcePaths.dpPath);
                    dir.mkdir();
                } catch (Exception e1) {

                }

                dpPath = file;
                bigProfileImage.setFill(Images.getImagePatternFromFile(this, file.getPath()));

            }
        });
    }

    public boolean addEmployee() {
        boolean ready = noEmptyFields();
        if (ready) {
            String uuid = UUID.randomUUID().toString();

            Employee emp = new Employee();
            emp.setPre_empNo(Integer.parseInt(Strings.preDate()));
            emp.setLastName(tf_lastName.getText());
            emp.setFirstName(tf_firstName.getText());
            emp.setMiddleName(tf_middleName.getText());

            if (male.isSelected()) {
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
                    DateUtils.wordToInt(birthdayPicker.getValue().getMonth().toString())
                    + "-" + birthdayPicker.getValue().getDayOfMonth());
            emp.setHireDate(hireDatePicker.getValue().getYear() + "-" +
                    DateUtils.wordToInt(hireDatePicker.getValue().getMonth().toString())
                    + "-" + hireDatePicker.getValue().getDayOfMonth());
            emp.setSchedule(buildSchedule());

            emp.setTime(scheduleTimePicker.getTime().toString());

            emp.setPagIbig((tf_pagIbig.getText()));
            emp.setSss(tf_sss.getText());

            emp.setImageUUID(uuid);
            emp.setCommission(tf_commission.getText());
            emp.setHourlyRate(tf_ratePerHour.getText());

            new EmployeeWriter().addEmployee(emp);

            try {
                FileUtils.copyFile(dpPath, new File(ResourcePaths.dpPath + uuid));
            } catch (IOException e1) {
                System.out.println("Directory already exists.");
            }
            refreshFields();
            Logger.log(Logger.LogType.EMP_ADD, ActiveUser.getUsername(), "Added " + emp.getPre_empNo() +
                    "" + emp.getPost_empNo());
            return ready;
        } else {
            AlertUtils.showAlert("Make sure there are no empty fields " +
                    "and you have chosen a picture..", "Also make sure that SSS " +
                    "and Pag-ibig are not out of range (Maximum of 5 numbers).");
        }
        return ready;
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

    private boolean noEmptyFields() {

        try {
            System.out.println(birthdayPicker.getValue().getMonthValue());
        } catch (Exception e) {
            return false;
        }

        if (tf_firstName.getText().length() > 0 &&
                tf_middleName.getText().length() > 0 &&
                tf_middleName.getText().length() > 0 &&
                tf_lastName.getText().length() > 0 &&
                tf_phoneNumber.getText().length() > 0 &&
                tf_address.getText().length() > 0 &&
                tf_contactPerson.getText().length() > 0 &&
                tf_contactAddress.getText().length() > 0 &&
                tf_contactPersonNumber.getText().length() > 0 &&
                tf_pagIbig.getText().length() > 0 &&
                tf_sss.getText().length() > 0 &&
                dpPath != null &&
                tf_sss.getText().length() < 6 &&
                tf_pagIbig.getText().length() < 6 &&
                tf_phoneNumber.getText().length() <= 12 &&
                tf_contactPersonNumber.getText().length() <= 12
                ) {
            return true;
        } else {
            return false;
        }
    }

    private void refreshFields() {
        tf_firstName.setText("");
        tf_lastName.setText("");
        tf_middleName.setText("");
        tf_phoneNumber.setText("");
        tf_address.setText("");
        tf_contactAddress.setText("");
        tf_contactPerson.setText("");
        tf_contactPersonNumber.setText("");
        tf_pagIbig.setText("");
        tf_sss.setText("");
        file = null;

        m.setSelected(true);
        t.setSelected(true);
        w.setSelected(true);
        th.setSelected(true);
        f.setSelected(true);
        s.setSelected(true);
        su.setSelected(true);

        birthdayPicker.setValue(null);
        bigProfileImage.setFill(Images.getImagePattern(this, ResourcePaths.camPath));
    }
}
