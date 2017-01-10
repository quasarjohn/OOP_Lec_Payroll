package control;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


/**
 * Created by John on 1/11/2017.
 */
public class AddEmployeeController {

    //WIRE VIEWS
    @FXML
    private TextField tf_firstName, tf_middleName, tf_lastName, tf_phoneNumber, tf_address,

            tf_hireDate, tf_birthday,

            tf_contactPerson, tf_contactAddress, tf_contactPhoneNumber,

            tf_pagIbig, tf_sss;

    @FXML private CheckBox m, t, w, th, f, s, su;

    private EmployeeController context;

    public AddEmployeeController(EmployeeController context) {
        this.context = context;

    }
}
