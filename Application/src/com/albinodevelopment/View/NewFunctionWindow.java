/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Logging.PriorityLogger;
import com.albinodevelopment.Model.Components.Function;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author conno
 */
public class NewFunctionWindow extends Window implements Initializable {

    @FXML
    private TextField functionNameTF;

    @FXML
    private TextField functionLimitTF;

    @FXML
    private Label output;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void handleSubmitButton(ActionEvent event) {

    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        cancel();
    }

    private void cancel() {
        
    }
}
