/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.Function;
import com.albinodevelopment.View.View;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class TabContentController extends TabContent implements Initializable {

    private String tabName;
    private View main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public Parent generateContent(Function input) {
        if(input != null){
            // take function
            // generate GUI elements
            // return Parent
        }
        
        return null;
    }
    
    private HBox generateDrinkGUI(Drink drink){
        return null;
    }
    
    private VBox generateDrinksListGUI(DrinksList drinksList){
        return null;
    }

}
