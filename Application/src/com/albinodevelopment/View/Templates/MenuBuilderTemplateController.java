/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Model.Components.Builders.MenuBuilder;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author conno
 */
public class MenuBuilderTemplateController extends MenuBuilderTemplate implements Initializable {
    
   @FXML
    private VBox scrollVbox;

    @FXML
    private TextField itemNameTF;

    @FXML
    private TextField itemPriceTF;

    @FXML
    private Button addItemButton;

    @FXML
    private Label output;

    @FXML
    private TextField menuTitleTF;

    @FXML
    private Button saveButton;
    
    @FXML
    private Label menuTitle;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @FXML
    private void addItemButtonAction(ActionEvent event) {
//        String name = drinkName.getText();
//        String price = drinkPrice.getText();
//        if (textFieldEntered(name, price)) {
//            main.getCommandHandler().handle(new ControllerCommand.ValidateDrinkCreationCommand(name, price));
//        }
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
//        String name = drinksListName.getText();
//        if (textFieldEntered(name, "TEXT TO HACK THE METHOD") && MenuBuilder.getInstance().get() != null) {
//            MenuBuilder.getInstance().get().setName(name);
//            //saveDrinksList(DrinksListBuilder.getInstance().get());
//            main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.SaveDrinksListCommand())));
//            drinksListName.clear();
//            scrollVbox.getChildren().clear();
//            output.setText("Drinks list saved as: " + name + ".ser");
//        } else {
//            output.setText("Please enter a drink list name.");
//        }
    }

    @FXML
    private void openButtonAction(ActionEvent event) {
//        main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.OpenDrinksListCommand())));
    }
    
    @FXML
    private void changeDrinksListNameButtonAction(ActionEvent event) {
//        main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.OpenDrinksListCommand())));
    }
}
