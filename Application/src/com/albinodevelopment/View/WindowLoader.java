/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Logging.PriorityLogger;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author conno
 * @param <T>
 */
public class WindowLoader<T extends Window> {

    public T NewWindow(String fxml, Class<T> windowClass) throws InstantiationException, IllegalAccessException {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            PriorityLogger.Log("ERROR: Window Loader couldn't load your window - " + ex.toString(), PriorityLogger.PriorityLevel.High);
        }

        T t = windowClass.newInstance();
        t.SetStage(stage);
        return t;
    }
}
