/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.DrinksTabItem;
import com.albinodevelopment.Model.Components.Function;
import java.net.URL;
import java.util.ResourceBundle;
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
public class DrinkItemContentFXMLController extends DrinkItemContent implements Initializable {

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public Parent generateContent(DrinksTabItem input) {
        //setupLabels
        setupLabels(input);
        return fromFXML;
    }

    private void setupLabels(DrinksTabItem drinksTabItem) {
        drinkName.setText(drinksTabItem.getDrink().GetName());
        drinkPrice.setText(String.valueOf(drinksTabItem.getDrink().GetPrice())); 
        currentAmt.setText(String.valueOf(drinksTabItem.getAmountUsed()));
        subtotal.setText(String.valueOf(drinksTabItem.getSubtotal()));
    }

}
