package com.albinodevelopment.View;

import com.albinodevelopment.IO.SerializerDeserializerFactory;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ApplicationSettings;
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

    private static WindowLoaderFactory windowLoaderFactory = new WindowLoaderFactory();

    @Override
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.runLater(() -> {
                ConnorLogger.Log("Application shutdown!",
                        ConnorLogger.PriorityLevel.High);
                SerializerDeserializerFactory.getSerializer(
                        com.albinodevelopment.Settings.ApplicationSettings.class)
                        .serializeApplicationSettings();
                System.exit(0);
            });
        });
        LoadWindow(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void LoadWindow(Stage stage) {
        WindowLoader windowLoader = windowLoaderFactory
                .getWindowLoader(com.albinodevelopment.View.MainWindow.class);
        try {
            MainWindow mainWindow = (MainWindow) windowLoader.NewWindow(
                    "MainWindowFXML.fxml", stage);
            mainWindow.Show();
            //mainWindow.start();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(TallyTab.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

}
