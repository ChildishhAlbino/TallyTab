/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.JFileChooser;

/**
 *
 * @author conno
 */
public class SettingsWindow extends Window {

    @FXML
    private Button directorySetButton;

    @FXML
    private StackPane settingsWindowStackPane;

    @FXML
    private ToggleGroup DrinksListInterpreter;

    @FXML
    private VBox vBox;

    @FXML
    private Button resetToDefaultButton;

    @FXML
    private Label directoryLabel; // the label in question

    public SettingsWindow() {
        super();
    }

    @Override
    protected void refresh() {
        ConnorLogger.log("Settings Window Refreshed.", ConnorLogger.PriorityLevel.Zero);
        // returns a null pointer exception
        Platform.runLater(() -> {
            directoryLabel.setText((String) ApplicationSettings.getInstance().getSetting(ISettingsManager.settingsList.DrinksListDirectory).getValue());
        });
    }

    @FXML
    public void HandleDirectoryChangeButton(ActionEvent event) {
        ConnorLogger.log("Handling directory change", ConnorLogger.PriorityLevel.Low);
        String directory = FileIO.openDirectoryWindow(FileIO.APPLICATION_DIRECTORY());
        stage.requestFocus();
        if (directory != null) {
            ApplicationSettings.getInstance().getSetting(ISettingsManager.settingsList.DrinksListDirectory).change(directory);
        } else {
            ConnorLogger.log("ERROR: Driectory was null.", ConnorLogger.PriorityLevel.Low);
        }
    }

    @FXML
    public void handleResetDefaultButton(ActionEvent event) {
        ApplicationSettings.getInstance().getSetting(ISettingsManager.settingsList.DrinksListDirectory).setToDefault();
        ConnorLogger.log("NOTE: FileDirectory set to default.", ConnorLogger.PriorityLevel.Low);
    }

    @FXML
    public void HandleMouseMoved(Event event) {
        //PriorityLogger.Log("Mouse Moved!", PriorityLogger.PriorityLevel.Low);
    }

}
