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
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.View.View;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class TabContentController extends TabContent implements Initializable {

    private final ContentLoaderFactory contentLoaderFactory = new ContentLoaderFactory();
    private final HashMap<DrinksTabItem, DrinkItemContent> drinkItemContents = new HashMap<>();

    @FXML
    private Label title;

    @FXML
    private Label limit;

    @FXML
    private Label currentVal;

    @FXML
    private Label percentage;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox drinksVbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public Parent generateContent(Function input) {
        if (input != null) {
            update(input);
            // return Parent
        }
        return fromFXML;
    }

    private void setupInfoPage(Function input) {
        title.setText(input.GetName());
        limit.setText(input.GetLimit());
        currentVal.setText(input.GetCurrentVal());
        percentage.setText(input.GetPercent());
        progressBar.setProgress(input.GetPercentAsDouble());
    }

    private void generateDrinksListGUI(DrinksTab drinksTab) {
        drinksTab.getDrinksList().getDrinksList().values().forEach((Drink drink) -> {
            DrinksTabItem drinksTabItem = new DrinksTabItem(drink, drinksTab.GetCount(drink), drinksTab.getDrinkSubtotal(drink));
            DrinkItemContent drinkItemContent = generateDrinkItemContent(drinksTabItem);
            Parent drinkContent = drinkItemContent.generateContent(drinksTabItem);
            drinksVbox.getChildren().add(drinkContent);
        });
    }

    private DrinkItemContent generateDrinkItemContent(DrinksTabItem drinksTabItem) {
        DrinkItemContent drinkItemContent = (DrinkItemContent) contentLoaderFactory.getBuilder().getContentController("DrinkItemContentFXML.fxml");
        drinkItemContent.setMain(view);
        drinkItemContent.setTabContent(this);
        drinkItemContents.put(drinksTabItem, drinkItemContent);
        return drinkItemContent;
    }

    @Override
    public void updateDrinkContent(DrinksTabItem drinksTabItem) {
        drinkItemContents.get(drinksTabItem.getDrink()).update(drinksTabItem);
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void update(Function input) {
        // take function
        setupInfoPage(input);
        // generate GUI elements
        if(drinkItemContents.isEmpty()){
            generateDrinksListGUI(input.getDrinksTab());
        } else {
            for (DrinkItemContent drinkItemContent : drinkItemContents.values()){
                drinkItemContent.update(input.getDrinksTab().getDrinksTabItem(drinkItemContent.drink));
            }
        }
    }

}
