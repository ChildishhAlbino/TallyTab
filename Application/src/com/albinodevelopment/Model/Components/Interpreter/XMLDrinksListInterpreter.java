/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import org.jdom2.*;

/**
 *
 * @author conno
 */
public class XMLDrinksListInterpreter implements IDrinksListInterpreter {

    @Override
    public DrinksList interpret(String directoryt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(DrinksList drinksList) {
        Document document = new Document();
        Element root = new Element("DrinksList");
        root.setAttribute("Version", "1");
        root.setAttribute("Name", drinksList.getName());
        document.setRootElement(root);
        for (Drink drink : drinksList.getDrinksList().values()) {
            Element drinkContainer = new Element("Drink");
            Element nameContainer = new Element("Name");
            nameContainer.addContent(drink.getName());
            Element priceContainer = new Element("Price");
            priceContainer.addContent(String.valueOf(drink.getPrice()));
            drinkContainer.addContent(nameContainer);
            drinkContainer.addContent(priceContainer);
            root.addContent(drinkContainer);
        }
    }

}
