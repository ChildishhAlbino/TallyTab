/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Model.Components.Builders.MenuBuilder;
import com.albinodevelopment.Model.Components.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 *
 * @author conno
 */
public class MenuBuilderItemTemplateController extends MenuBuilderItemTemplate implements Initializable {

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemPriceLabel;

    private MenuItem item;

    @FXML
    private void removeButtonAction(ActionEvent e) {
        view.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.ValidateDrinkRemovalCommand(item)));
        view.handle(new ViewCommand.LoadMenuCommand(MenuBuilder.getInstance().get()));
    }

    @Override
    public Parent generate(MenuItem input) {
        if (input != null) {
            update(input);
        }
        return fromFXML;
    }

    @Override
    public String getText() {
        return item.getName();
    }

    @Override
    public void update(MenuItem input) {
        item = input;
        itemNameLabel.setText(item.getName());
        String itemPrice = String.valueOf(item.getPrice());
        itemPriceLabel.setText(itemPrice);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
