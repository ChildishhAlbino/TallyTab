/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Logging.PriorityLogger;
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
    private Label directoryLabel; // the label in question

    public SettingsWindow() {
        super();
    }

    @Override
    protected void Refresh() {
        PriorityLogger.Log("Settings Window Refreshed.", PriorityLogger.PriorityLevel.Low);
        // returns a null pointer exception
        Platform.runLater(() -> {
            directoryLabel.setText((String) ApplicationSettings.GetInstance().getSetting(ISettingsManager.settingsList.TextFileDirectory).getValue());
        });
    }

    @FXML
    public void HandleDirectoryChangeButton(ActionEvent event) {
        PriorityLogger.Log("Handling directory change", PriorityLogger.PriorityLevel.Low);
        String directory = OpenDirectoryWindow();
        if (directory != null) {
            ApplicationSettings.GetInstance().getSetting(ISettingsManager.settingsList.TextFileDirectory).change(directory);
        }
    }

    @FXML
    public void HandleMouseMoved(Event event) {
        //PriorityLogger.Log("Mouse Moved!", PriorityLogger.PriorityLevel.Low);
    }

    private String OpenDirectoryWindow() {
        String s = null;
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = jFileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            s = jFileChooser.getSelectedFile().toString();
        } else {
            PriorityLogger.Log("ERROR: Open file operation was cancelled.", PriorityLogger.PriorityLevel.Low);
        }
        return s;
    }

}
