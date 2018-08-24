/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.DrinksListBuilder;

import com.albinodevelopment.Logging.PriorityLogger;
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
    private DrinksList temporary;

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
    private void createButtonAction(ActionEvent event) {
        String name = drinkName.getText();
        String price = drinkPrice.getText();
        if (textFieldEntered(name, price)) {
            if (temporary == null) {
                temporary = newDrinksList();
            }
            Drink drink = createDrink(name, price);
            if (drink != null) {
                createGUIFromDrink(drink);
                temporary.add(drink);
                output.setText("Drink created!");
                PriorityLogger.Log("Drink created succesfully!\n" + drink.toString(), PriorityLogger.PriorityLevel.Low);
            } else {
                PriorityLogger.Log("ERROR: Drink was null.", PriorityLogger.PriorityLevel.Low);
            }
        }
    }

    private void createGUIFromDrink(Drink drink) {
        Button removeButton = new Button("Remove Item");
        removeButton.setOnAction((event) -> {
            removeItem(removeButton.getParent());
        });
        Label drinkNameLabel = new Label(drink.GetName());
        Label drinkPriceLabel = new Label(String.valueOf(drink.GetPrice()));
        HBox hBox = new HBox(75, setLabelSize(drinkNameLabel), 
                setLabelSize(drinkPriceLabel), removeButton);
        VBox.setMargin(hBox, new Insets(10, 0, 0, 10));
        scrollVbox.getChildren().add(hBox);
    }

    private void removeItem(Parent parent) {
        PriorityLogger.Log("Removing item!", PriorityLogger.PriorityLevel.Low);
        Label drinkNameLabel = (Label) parent.getChildrenUnmodifiable().get(0);
        Label drinkPriceLabel = (Label) parent.getChildrenUnmodifiable().get(1);
        temporary.remove(createDrink(drinkNameLabel.getText(), 
                drinkPriceLabel.getText()));
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

    private Drink createDrink(String name, String price) {
        try {
            Double d_Price = Double.valueOf(price);
            return new Drink(d_Price, name);
        } catch (NumberFormatException ex) {
            PriorityLogger.Log("ERROR: Price couldn't be converted to double - " + ex.toString(), PriorityLogger.PriorityLevel.Medium);
        }
        return null;
    }

    private DrinksList newDrinksList() {
        return new DrinksList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    protected void Refresh() {
        if (temporary != null && temporary.GetListSize() > 0) {
            PriorityLogger.Log(temporary.toString(), PriorityLogger.PriorityLevel.Low);
        }
        PriorityLogger.Log("Drinks List Window refreshed.", PriorityLogger.PriorityLevel.Zero);
    }
}
