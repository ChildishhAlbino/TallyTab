/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.Command;
import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ICommand.ExecutionResult;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Logging.PriorityLogger;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author conno
 */
public class MainWindow extends Thread implements Initializable, ICommandHandler<ViewCommand>, IView {
    
    private ICommandHandler<ControllerCommand> commandHandler;
    @FXML
    private TabPane tabPane;
    
    public MainWindow() {
        StartUp();
    }
    
    @FXML
    private void decreaseButtonAction(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            // get drink
            String drink = GetDrinkNameFromGUI(button);
            PriorityLogger.Log(drink, PriorityLogger.PriorityLevel.Low);
            // pass to drinks manager
        } else {
            // log error 
            PriorityLogger.Log("ERROR: Source was not button.", PriorityLogger.PriorityLevel.Medium);
        }
    }
    
    @FXML
    private void increaseButtonAction(ActionEvent event) {
        // check which drink was increased
        if (event.getSource() instanceof Button) {
            
            Button button = (Button) event.getSource();
            // get drink
            String drink = GetDrinkNameFromGUI(button);
            PriorityLogger.Log(drink, PriorityLogger.PriorityLevel.Low);
            // pass to drinks manager
        } else {
            // log error 
            PriorityLogger.Log("ERROR: Source was not button.", PriorityLogger.PriorityLevel.Medium);
        }
    }
    
    @FXML
    private void handleNewButton(ActionEvent event) {
        New();
    }
    
    @FXML
    private void handleOpenButton(ActionEvent event) {
        Open();
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        Save();
    }
    
    @FXML
    private void handleCloseButton(ActionEvent event) {
        //Close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void Handle(ViewCommand command) {
        if (command.CanExecute(this)) {
            ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ExecutionResult.failure)) {
                // log
                PriorityLogger.Log(command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            }
        }
    }
    
    @Override
    public void SetCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }
    
    private void StartUp() {
        Controller controller = new Controller();
        Model model = new Model();
        model.SetCommandHandler(this);
        this.SetCommandHandler(controller);
        controller.SetCommandHandler(model);
        controller.start();
    }
    
    @Override
    public ICommandHandler GetCommandHandler() {
        return commandHandler;
    }
    
    @Override
    public boolean CanHandle(Command command) {
        if (command instanceof ViewCommand) {
            // log success
            PriorityLogger.Log(command.toString() + ": Success.", PriorityLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.GenerateErrorCode("This command cannot be handled by this Command Handler.");
            PriorityLogger.Log(command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            return false;
        }
    }
    
    @Override
    public void CreateDrinkGUIElements(Drink drink) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void Refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String GetDrinkNameFromGUI(Button button) {
        String drinkName = null;
        Node n = button.getParent().getChildrenUnmodifiable().get(0);
        if (n instanceof Label) {
            PriorityLogger.Log("Label found!", PriorityLogger.PriorityLevel.Medium);
            Label drinkNameLabel = (Label) n;
            drinkName = drinkNameLabel.getText();
        } else {
            PriorityLogger.Log("ERROR: Node was not label.", PriorityLogger.PriorityLevel.Medium);
        }
        return drinkName;
    }
    
    @Override
    public void New() {
        // open new function window
        // create new tab with title == to name

        Tab tab = new Tab("New Function");
        tab.setOnClosed((Event event) -> {
            TabClosed();
        });
        tab.contentProperty().set(GenerateNewFunctionPane("New Function"));
        tabPane.getTabs().add(tab);
        
    }
    
    private StackPane GenerateNewFunctionPane(String name) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new Button("Lol"));
        return stackPane;
    }
    
    
    @Override
    public void Open() {
        // read in a function file and open a tab with it's corresponding details
    }
    
    @Override
    public void Save() {
        // create a function file and write it to the disk
    }
    
    @Override
    public void TabClosed() {
        PriorityLogger.Log("Tab closed", PriorityLogger.PriorityLevel.Low);
    }
    
}
