package com.albinodevelopment.View;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ApplicationSettingsXMLInterpreter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author conno
 */
public class TallyTab extends Application {

    private static final WindowLoaderFactory windowLoaderFactory = new WindowLoaderFactory();

    @Override
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.runLater(() -> {
                ConnorLogger.log("Application shutdown!",
                        ConnorLogger.PriorityLevel.High);
                ApplicationSettingsXMLInterpreter.save(ApplicationSettings.getInstance());
                System.exit(0);
            });
        });        ApplicationSettings.getInstance();
        loadWindow(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnorLogger.setLoggingState(true);
        ConnorLogger.setPriority(ConnorLogger.PriorityLevel.Low);
        launch(args);
    }

    private void loadWindow(Stage stage) {
        WindowLoader windowLoader = windowLoaderFactory
                .getWindowLoader(com.albinodevelopment.View.MainWindow.class);
        try {
            MainWindow mainWindow = (MainWindow) windowLoader.newWindow(
                    "MainWindowFXML.fxml", stage);
            mainWindow.show();
            mainWindow.start();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(TallyTab.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

}
