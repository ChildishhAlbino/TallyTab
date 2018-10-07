/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Logging.ConnorLogger;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author conno
 * @param <T>
 */
public class WindowLoader<T extends Window> {

    private final FXMLLoader loader = new FXMLLoader();

    public T newWindow(String fxml) throws InstantiationException, IllegalAccessException {
        Stage stage = new Stage();
        return newWindow(fxml, stage);
    }

    public T newWindow(String fxml, Stage stage) throws InstantiationException, IllegalAccessException {
        try {
            loader.setLocation(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("TallyTab");

        } catch (IOException ex) {
            ConnorLogger.log("ERROR: Window Loader couldn't load your window - " + ex.toString(), ConnorLogger.PriorityLevel.High);
        }
        T t = (T) loader.getController();
        stage.setOnShowing((WindowEvent event) -> {
            t.refresh();
        });
        t.setStage(stage);
        return t;
    }
}
