/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.MenuBuilder;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.MenuItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author conno
 */
public class MenuBuilderTemplateController extends MenuBuilderTemplate implements Initializable {

    TemplateLoaderFactory templateLoaderFactory = new TemplateLoaderFactory();

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

    private ArrayList<MenuBuilderItemTemplate> menuBuilderItemTeplates = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addItemButtonAction(ActionEvent event) {
        String name = itemNameTF.getText();
        String price = itemPriceTF.getText();
        if ((notBlank(itemNameTF) && notBlank(itemPriceTF))) {
            view.getCommandHandler().handle(new ControllerCommand.ValidateDrinkCreationCommand(name, price));
        }
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        view.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.SaveDrinksListCommand())));
        this.update(MenuBuilder.getInstance().get());
    }

    @FXML
    private void openButtonAction(ActionEvent event) {
        view.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.OpenDrinksListCommand())));
    }

    @FXML
    private void changeDrinksListNameButtonAction(ActionEvent event) {
//        main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.OpenDrinksListCommand())));
    }

    @Override
    public Parent generate(Menu input) {
        if (input != null) {
            update(input);
            // return Parent
        }
        if (fromFXML == null) {
            ConnorLogger.log("MenuBuilderTemplate fxml was null.", ConnorLogger.PriorityLevel.Medium);
        }
        return fromFXML;
    }

    @Override
    public String getText() {
        return menuTitle.getText();
    }

    @Override
    public void update(Menu input) {
        menuTitle.setText(input.getName());
        TreeMap<String, MenuItem> menuMap = input.getDrinksMap();
        menuBuilderItemTeplates.clear();
        if (scrollVbox.getChildren().size() > 1) {
            scrollVbox.getChildren().remove(1, scrollVbox.getChildren().size());
        }
        for (MenuItem item : menuMap.values()) {
            MenuBuilderItemTemplate menuBuilderItemTemplate
                    = (MenuBuilderItemTemplate) templateLoaderFactory.getBuilder().getContentController("MenuBuilderItemTemplate.fxml");
            menuBuilderItemTemplate.setMain(view);
            Parent p = menuBuilderItemTemplate.generate(item);
            scrollVbox.getChildren().add(p);
            menuBuilderItemTeplates.add(menuBuilderItemTemplate);
        }
    }

    private boolean notBlank(TextField tf) {
        boolean notBlank = tf.getText() != "";
        if(notBlank){
            tf.clear();
        }
        return notBlank;
    }

}
