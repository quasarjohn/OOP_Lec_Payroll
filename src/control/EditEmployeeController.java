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
public class EditEmployeeController {

    //WIRE VIEWS
    @FXML
    private JFXTextField tf_firstName, tf_middleName, tf_lastName, tf_phoneNumber, tf_address,

    tf_contactPerson, tf_contactAddress, tf_contactPersonNumber,

    tf_pagIbig, tf_sss, tf_commission;

    @FXML
    private Circle bigProfileImage;

    @FXML
    private JFXCheckBox m, t, w, th, f, s, su;

    @FXML
    JFXDatePicker hireDatePicker, birthdayPicker, scheduleTimePicker;

    @FXML
    private JFXRadioButton female, male;

    @FXML
    private JFXToggleButton statusToggle;

    private File dpPath;

    private EmployeeController context;

    private String uuid;

    private Employee emp;

    public EditEmployeeController(EmployeeController context) {
        this.context = context;
    }

    public void initComponents() {
        scheduleTimePicker.setShowTime(true);
        scheduleTimePicker.setTime(LocalTime.of(10, 00));
        scheduleTimePicker.setEditable(false);

        hireDatePicker.setValue(LocalDate.now());

        FilterUtils.restrictToNumbers(tf_pagIbig);
        FilterUtils.restrictToNumbers(tf_sss);
        FilterUtils.restrictToNumbers(tf_phoneNumber);
        FilterUtils.restrictToNumbers(tf_contactPersonNumber);

        bigProfileImage.setFill(Images.getImagePattern(this, ResourcePaths.camPath));

        bigProfileImage.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg"));
            File file = fc.showOpenDialog(Domain.getPrimaryStage());

            if (file != null) {
                File dir = null;

                try {
                    dir = new File(ResourcePaths.dpPath);
                    dir.mkdir();
                } catch (Exception e1) {
                    System.out.println("Path already exists.");
                }

                dpPath = file;
                bigProfileImage.setFill(Images.getImagePatternFromFile(this, file.getPath()));
                dpChanged = true;
                uuid = UUID.randomUUID().toString();
            }
        });
    }

    private boolean dpChanged = false;

    public boolean updateEmployee() {

        System.out.println(ActiveUser.getUsername());

        boolean ready = noEmptyFields();

        if (ready) {
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
                    DateUtils.wordToInt(birthdayPicker.getValue().getMonth().toString())
                    + "-" + birthdayPicker.getValue().getDayOfMonth());
            emp.setHireDate(hireDatePicker.getValue().getYear() + "-" +
                    DateUtils.wordToInt(hireDatePicker.getValue().getMonth().toString())
                    + "-" + hireDatePicker.getValue().getDayOfMonth());
            emp.setSchedule(buildSchedule());

            emp.setTime(scheduleTimePicker.getTime().toString());

            emp.setPagIbig(tf_pagIbig.getText());
            emp.setSss(tf_sss.getText());
            emp.setPre_empNo(this.emp.getPre_empNo());
            emp.setPost_empNo(this.emp.getPost_empNo());
            emp.setCommission(tf_commission.getText());

            if (statusToggle.isSelected()) {
                emp.setEmpStatus("ACTIVE");
            } else {
                emp.setEmpStatus("INACTIVE");
            }

            if (dpChanged) {
                emp.setImageUUID(uuid);
            } else {
                emp.setImageUUID(this.emp.getImageUUID());
            }

            new EmployeeWriter().updateEmployee(emp);

            if (dpChanged) {
                try {
                    FileUtils.copyFile(dpPath, new File(ResourcePaths.dpPath + uuid));
                } catch (IOException e1) {
                    System.out.println("Directory already exists.");
                }
            }

            if (emp.getEmpStatus().equals("INACTIVE"))
                Logger.log(Logger.LogType.EMP_DELETE, ActiveUser.getUsername(), "Soft Deleted " +
                        emp.getPre_empNo() + "" + emp.getPost_empNo());
            else
                Logger.log(Logger.LogType.EMP_UPDATE, ActiveUser.getUsername(), "Updated " +
                        emp.getPre_empNo() + "" + emp.getPost_empNo());
        } else {
            AlertUtils.showAlert("Make sure there are no empty fields.", "Also make sure that SSS " +
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

    public void bindViews(Employee emp) {
        this.emp = emp;

        if (emp.getEmpStatus().equalsIgnoreCase("ACTIVE")) {
            statusToggle.setSelected(true);
        } else {
            statusToggle.setSelected(false);
        }

        tf_firstName.setText(emp.getFirstName());
        tf_lastName.setText(emp.getLastName());
        tf_middleName.setText(emp.getMiddleName());
        tf_phoneNumber.setText(emp.getPhoneNumber());
        tf_address.setText(emp.getAddress());
        tf_contactAddress.setText(emp.getContactPersonAddress());
        tf_contactPerson.setText(emp.getContactPerson());
        tf_pagIbig.setText(emp.getPagIbig() + "");
        tf_sss.setText(emp.getSss() + "");
        tf_commission.setText(emp.getCommission());

        formatScheduleSelection(emp.getSchedule().trim());

        if (emp.getGender().equals("M"))
            male.setSelected(true);
        else female.setSelected(true);

        tf_contactPersonNumber.setText(emp.getContactPersonNumber());

        int[] birthDateInt = DateUtils.dateToInt(emp.getBirthDate());
        birthdayPicker.setValue(LocalDate.of(birthDateInt[0], birthDateInt[1], birthDateInt[2]));
    }

    private void formatScheduleSelection(String sched) {
        String[] scheds = sched.split(" ");

        m.setSelected(false);
        t.setSelected(false);
        w.setSelected(false);
        th.setSelected(false);
        f.setSelected(false);
        s.setSelected(false);
        su.setSelected(false);

        for (String x : scheds) {
            if (x.equals("M"))
                m.setSelected(true);
            else if (x.equals("T"))
                t.setSelected(true);
            else if (x.equals("W"))
                w.setSelected(true);
            else if (x.equals("TH"))
                th.setSelected(true);
            else if (x.equals("F"))
                f.setSelected(true);
            else if (x.equals("S"))
                s.setSelected(true);
            else if (x.equals("SU"))
                su.setSelected(true);
        }
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
                tf_sss.getText().length() < 6 &&
                tf_pagIbig.getText().length() < 6
                ) {
            return true;
        } else {
            return false;
        }
    }
}
