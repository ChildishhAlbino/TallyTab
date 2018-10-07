/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
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
    private Label selectedDrinksList;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button openDrinksListButton;

    @FXML
    private void handleSubmitButton(ActionEvent event) {
        boolean boxEmpty = false;
        if ("".equals(functionNameTF.getText())) {
            boxEmpty = true;
        }
        if ("".equals(selectedDrinksList.getText())) {
            boxEmpty = true;
        }

        if (boxEmpty) {
            output("Please enter a function name or select a drinks list.");
        } else {
            submit();
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        cancel();
    }

    @FXML
    private void handleOpenDrinksListButton(ActionEvent event) {
        String directory = FileIO.openFileExplorer((String) ApplicationSettings
                .getInstance().getSetting(ISettingsManager.settingsList.SerializedDirectory).getValue() + "\\DrinksList");
        if (directory != null) {
            selectedDrinksList.setText(directory);
        }
    }

    private void cancel() {
        close();
    }

    private void submit() {
        main.handle(new ViewCommand.PassToControllerCommand(
                new ControllerCommand.ValidateNewFunctionCommand(functionNameTF
                        .getText(), functionLimitTF.getText(), selectedDrinksList.getText())));
    }

    @Override
    public void output(String toBeOutput) {
        output.setText(toBeOutput);
    }

}
