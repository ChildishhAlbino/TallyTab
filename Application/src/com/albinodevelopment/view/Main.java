/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author conno
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // create MVC objects
        // link them
        // show gui

        SetupGUI(stage);
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

}
