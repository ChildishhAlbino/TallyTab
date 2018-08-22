package com.albinodevelopment.View;

import com.albinodevelopment.Logging.PriorityLogger;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private static WindowLoaderFactory<MainWindow> windowLoaderFactory = new WindowLoaderFactory<MainWindow>();

    @Override
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.runLater(() -> {
                PriorityLogger.Log("Application shutdown!", PriorityLogger.PriorityLevel.High);
                System.exit(0);
            });
        });
        //SetupGUI(stage);
        LoadWindow(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void SetupGUI(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindowFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // log
        }
    }

    private void LoadWindow(Stage stage) {
        WindowLoader windowLoader = windowLoaderFactory.getWindowLoader();

        try {
            MainWindow mainWindow = (MainWindow) windowLoader.NewWindow(
                    "MainWindowFXML.fxml", com.albinodevelopment.View.MainWindow.class, stage);
            mainWindow.Show();
            mainWindow.start();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(TallyTab.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

}
