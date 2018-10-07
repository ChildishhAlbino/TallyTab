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
    protected View main;

    public Window() {
        timer = new Timer();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void refresh() {

    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.hide();
    }

    public void setupTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        }, 1000L, REFRESH_RATE);
    }

    @Override
    public void run() {
        setupTimer();
    }

    protected void addNode(Node node, Parent parent) {
        parent.getChildrenUnmodifiable().add(node);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    public void setMain(View main) {
        if (this.main == null) {
            this.main = main;
        }
    }

    protected void output(String toBeOutput) {

    }
}
