/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.MenuItemContainer;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class FunctionTemplateController extends FunctionTemplate implements Initializable {

    private final TemplateLoaderFactory templateLoaderFactory = new TemplateLoaderFactory();
    private final HashMap<MenuItemContainer, MenuItemTemplate> templates = new HashMap<>();
    
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
    public Parent generate(Function input) {
        if (input != null) {
            update(input);
            // return Parent
        }
        return fromFXML;
    }

    private void setupInfoPage(Function input) {
        title.setText(input.getName());
        limit.setText(input.getLimit());
        currentVal.setText(input.getCurrentValue());
        percentage.setText(input.getPercentUsed());
        progressBar.setProgress(input.getPercentAsDouble());
    }

    private void generateDrinksListGUI(DrinksTab drinksTab) {
        drinksTab.getDrinksList().getDrinksMap().values().forEach((Drink drink) -> {
            MenuItemContainer drinksTabContainer = new MenuItemContainer(drink, drinksTab.GetCount(drink), drinksTab.getDrinkSubtotal(drink));
            MenuItemTemplate drinkItemContent = generateDrinkItemContent(drinksTabContainer);
            Parent drinkContent = drinkItemContent.generate(drinksTabContainer);
            drinksVbox.getChildren().add(drinkContent);
        });
    }

    private MenuItemTemplate generateDrinkItemContent(MenuItemContainer menuItemContainer) {
        MenuItemTemplate menuItemContent = (MenuItemTemplate) templateLoaderFactory.getBuilder().getContentController("MenuItemTemplateFXML.fxml");
        menuItemContent.setMain(view);
        menuItemContent.setTabContent(this);
        templates.put(menuItemContainer, menuItemContent);
        return menuItemContent;
    }

    @Override
    public void updateDrinkContent(MenuItemContainer menuItemContainer) {
        templates.get(menuItemContainer).update(menuItemContainer);
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
        if (templates.isEmpty()) {
            generateDrinksListGUI(input.getDrinksTab());
        } else {
            for (MenuItemTemplate drinkItemContent : templates.values()) {
                drinkItemContent.update(input.getDrinksTab().getDrinksTabItem(drinkItemContent.drink));
            }
        }
    }
    
    @FXML
    public void saveFunctionButtonAction(ActionEvent event){
        ConnorLogger.log("Saving function to XML!!", ConnorLogger.PriorityLevel.Low);
        FunctionManager.getInstance().saveFunction(title.getText());
    }

}
