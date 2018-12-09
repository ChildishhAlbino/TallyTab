/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.MenuBuilder;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.CustomerTab;
import com.albinodevelopment.Model.Components.MenuItemContainer;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import com.albinodevelopment.Model.Components.Item;
import com.albinodevelopment.Model.Components.Menu;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField limitEditTF;

    @FXML
    private Label output;

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

    private void generateTabGUI(CustomerTab drinksTab) {
        drinksTab.getMenu().getMenuMap().values().forEach((MenuItem drink) -> {
            MenuItemContainer drinksTabContainer = new MenuItemContainer(drink, drinksTab.GetCount(drink), drinksTab.getItemSubtotal(drink));
            MenuItemTemplate drinkItemContent = generateMenuItemTemplate(drinksTabContainer);
            Parent drinkContent = drinkItemContent.generate(drinksTabContainer);
            drinksVbox.getChildren().add(drinkContent);
        });
    }

    private MenuItemTemplate generateMenuItemTemplate(MenuItemContainer menuItemContainer) {
        MenuItemTemplate menuItemContent;
        if (menuItemContainer.getDrink().getItemState() == Item.ItemState.open) {
            menuItemContent = (MenuItemTemplate) templateLoaderFactory.getBuilder().getContentController("MenuItemTemplateFXML.fxml");
        } else {
            menuItemContent = (MenuItemTemplate) templateLoaderFactory.getBuilder().getContentController("MenuItemTemplateLOCKEDFXML.fxml");
        }

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
        if (input != null) {
            clear();
            setupInfoPage(input);
            generateTabGUI(input.getTab());
        }
    }

    @FXML
    public void saveFunctionButtonAction(ActionEvent event) {
        ConnorLogger.log("Saving function to XML!!", ConnorLogger.PriorityLevel.Low);
        FunctionManager.getInstance().saveFunction(title.getText());
    }

    @FXML
    public void editLimitButtonAction(ActionEvent event) {
        ConnorLogger.log("Validating limit edit request.", ConnorLogger.PriorityLevel.Low);
        String input = limitEditTF.getText();
        if (notBlank(limitEditTF)) {
            try {
                Double newLimit = Double.valueOf(input);
                view.handle(new ViewCommand.PushOutputMessasgeCommand(this, "Processing limit change."));
                view.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.EditLimitCommand(content, newLimit))));
            } catch (NumberFormatException ex) {
                view.handle(new ViewCommand.PushOutputMessasgeCommand(this, "Invalid limit."));
            }
        }
    }

    @FXML
    public void swapMenuButtonAction(ActionEvent event) {
        String directory = FileIO.openFileExplorer(FileIO.MENU_DIRECTORY());
        if (directory != null) {
            Menu newMenu = MenuBuilder.getInstance().openAndGet(directory);
            view.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.SwapMenuCommand(content, newMenu))));
        }
    }

    private boolean notBlank(TextField tf) {
        ConnorLogger.log(tf.getText(), ConnorLogger.PriorityLevel.Zero);
        boolean notBlank = !"".equals(tf.getText());
        if (notBlank) {
            tf.clear();
        } else {
            view.handle(new ViewCommand.PushOutputMessasgeCommand(this, "Please don't leave text field blank."));
        }
        return notBlank;
    }

    @Override
    public void output(String output) {
        if (output != null) {
            this.output.setText(output);
        }
    }

    private void clear() {
        if (drinksVbox.getChildren().size() > 0) {
            drinksVbox.getChildren().remove(1, drinksVbox.getChildren().size());
        }
        templates.clear();
    }

}
