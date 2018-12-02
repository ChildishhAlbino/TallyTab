/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.DrinksListBuilder;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.DrinksListBuilder;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.View.Window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author conno
 */
public class DrinksListBuilderWindow extends Window implements Initializable {

    private final String BLANK_TEXT = "";
    // private DrinksListBuilder drinksListBuilder = new DrinksListBuilder();

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

    @FXML
    private TextField drinksListName;

    @FXML
    private Button saveButton;

    @FXML
    private void createButtonAction(ActionEvent event) {
        String name = drinkName.getText();
        String price = drinkPrice.getText();
        if (textFieldEntered(name, price)) {
            main.getCommandHandler().handle(new ControllerCommand.ValidateDrinkCreationCommand(name, price));
        }
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        String name = drinksListName.getText();
        if (textFieldEntered(name, "TEXT TO HACK THE METHOD") && DrinksListBuilder.getInstance().get() != null) {
            DrinksListBuilder.getInstance().get().setName(name);
            //saveDrinksList(DrinksListBuilder.getInstance().get());
            main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.SaveDrinksListCommand())));
            drinksListName.clear();
            scrollVbox.getChildren().clear();
            output.setText("Drinks list saved as: " + name + ".ser");
        } else {
            output.setText("Please enter a drink list name.");
        }
    }

    @FXML
    private void openButtonAction(ActionEvent event) {
        main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.OpenDrinksListCommand())));
    }

    public void loadDrinksList(DrinksList drinksList) {
        drinksListName.setText(drinksList.getName());
        createGUIFromDrinksList(drinksList);
        output.setText("Loaded drinks list!");
    }

    private void createGUIFromDrinksList(DrinksList drinksList) {
        drinksList.getDrinksMap().values().forEach((drink) -> {
            createGUIFromDrink(drink);
        });
    }

    public void createGUIFromDrink(Drink drink) {
        Button removeButton = new Button("Remove Item");
        removeButton.setOnAction((event) -> {
            removeItem(removeButton.getParent());
        });
        Label drinkNameLabel = new Label(drink.getName());
        Label drinkPriceLabel = new Label(String.valueOf(drink.getPrice()));
        HBox hBox = new HBox(75, setLabelSize(drinkNameLabel),
                setLabelSize(drinkPriceLabel), removeButton);
        VBox.setMargin(hBox, new Insets(10, 0, 0, 10));
        scrollVbox.getChildren().add(hBox);
    }

    private void removeItem(Parent parent) {
        ConnorLogger.log("Removing item!", ConnorLogger.PriorityLevel.Low);
        Label drinkNameLabel = (Label) parent.getChildrenUnmodifiable().get(0);
        Label drinkPriceLabel = (Label) parent.getChildrenUnmodifiable().get(1);
        main.handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.ValidateDrinkRemovalCommand(drinkNameLabel.getText(), drinkPriceLabel.getText())));
        scrollVbox.getChildren().remove(parent);
        output.setText("Item removed!");
    }

    private Label setLabelSize(Label label) {
        label.setFont(new Font(label.getFont().getName(), 20));
        return label;
    }

    private boolean textFieldEntered(String name, String price) {
        if (name.equals(BLANK_TEXT) && price.equals(BLANK_TEXT)) {
            output.setText("Please enter a drink name and price.");
            return false;
        } else if (price.equals(BLANK_TEXT)) {
            output.setText("Please enter a drink price.");
            return false;
        } else if (name.equals(BLANK_TEXT)) {
            output.setText("Please enter a drink name.");
            return false;
        } else {
            drinkName.clear();
            drinkPrice.clear();
            return true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    protected void refresh() {
        if (DrinksListBuilder.getInstance().get() != null && DrinksListBuilder.getInstance().get().GetListSize() > 0) {
            ConnorLogger.log(DrinksListBuilder.getInstance().get().toString(), ConnorLogger.PriorityLevel.Zero);
        }
        ConnorLogger.log("Drinks List Window refreshed.", ConnorLogger.PriorityLevel.Zero);
    }
}
