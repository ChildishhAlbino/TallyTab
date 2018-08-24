/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.DrinksListBuilder;

import com.albinodevelopment.View.Window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class DrinksListBuilderWindow extends Window implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox scrollVbox;

    @FXML
    private TextField drinkName;

    @FXML
    private TextField drinkPrice;

    @FXML
    private Button createDrinkButton;

    @FXML
    private Label output;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
