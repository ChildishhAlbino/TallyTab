/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author conno
 */
public class MainWindow extends Thread implements Initializable, ICommandHandler<ViewCommand> {

    private ICommandHandler<ControllerCommand> commandHandler;
    
    
    public MainWindow(){
        StartUp();
    }
    
    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public boolean CanHandle(ViewCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Handle(ViewCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }
    
    private void StartUp(){
        Controller controller = new Controller();
        Model model = new Model();
        
        model.SetCommandHandler(this);
        this.SetCommandHandler(controller);
        controller.SetCommandHandler(model);
        controller.start();
    }
    
}
