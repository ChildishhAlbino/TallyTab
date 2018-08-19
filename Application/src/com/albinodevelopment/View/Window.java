/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import javafx.stage.Stage;

/**
 *
 * @author conno
 */
public abstract class Window extends Thread{

    protected Stage stage;

    public Window() {
        //
    }

    public void SetStage(Stage stage) {
        this.stage = stage;
    }

}
