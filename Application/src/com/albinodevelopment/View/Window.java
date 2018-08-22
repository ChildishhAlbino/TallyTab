/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author conno
 */
public abstract class Window extends Thread implements Initializable {

    protected Stage stage;
    protected Timer timer;
    protected final long REFRESH_RATE = 100L;

    public Window() {
        timer = new Timer();
    }

    public void SetStage(Stage stage) {
        this.stage = stage;
    }

    protected void Refresh() {

    }

    public void Show() {
        stage.show();
    }

    public void Close() {
        stage.hide();
    }

    public void SetupTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Refresh();
            }
        }, 1000L, REFRESH_RATE);
    }

    @Override
    public void run() {
        //PriorityLogger.Log(stage.getScene().getRoot().getChildrenUnmodifiable().toString(), PriorityLogger.PriorityLevel.Medium);
        SetupTimer();
    }

    protected void AddNode(Node node, Parent parent) {
        parent.getChildrenUnmodifiable().add(node);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
}
