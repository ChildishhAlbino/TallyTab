/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.DrinksTabItem;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.View.Window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class DrinkItemContentController extends DrinkItemContent implements Initializable {

    @FXML
    private Label drinkName;
    @FXML
    private Label drinkPrice;
    @FXML
    private Label currentAmt;
    @FXML
    private Label subtotal;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public Parent generateContent(DrinksTabItem input) {
        //setupLabels
        update(input);
        return fromFXML;
    }

    @Override
    public void update(DrinksTabItem input) {
        this.drink = input.getDrink();
        drinkName.setText(input.getDrink().GetName());
        drinkPrice.setText(String.valueOf(input.getDrink().GetPrice()));
        currentAmt.setText(String.valueOf(input.getAmountUsed()));
        subtotal.setText(String.valueOf(input.getSubtotal()));
    }

    @FXML
    private void handlePlusButton(ActionEvent event) {
        ConnorLogger.Log("+1 " + drink.GetName(), ConnorLogger.PriorityLevel.Medium);
        view.Handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.ValidateDrinkAmountChangeCommand(+1, drink, tabContent.getText())));
    }

    @FXML
    private void handleMinusButton(ActionEvent event) {
        ConnorLogger.Log("-1 " + drink.GetName(), ConnorLogger.PriorityLevel.Medium);
        view.Handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.ValidateDrinkAmountChangeCommand(-1, drink, tabContent.getText())));
    }

    @Override
    public String getText() {
        return drink.GetName();
    }

}
