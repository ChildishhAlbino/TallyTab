/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.Menu;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author conno
 */
public class XMLDrinksListInterpreter implements IDrinksListInterpreter, Serializable {

    @Override
    public Menu interpret(String directory) {
        Menu drinksList = new Menu();
        Document document = FileIO.getXMLDocumentFromFile(directory);
        if (document != null) {
            return interpret(document.getRootElement());
        }
        return null;
    }

    @Override
    public void save(Menu drinksList) {
        Document document = new Document();
        Element root = new Element("DrinksList");
        root.setAttribute("Version", "1");
        root.setAttribute("Name", drinksList.getName());
        document.setRootElement(root);
        drinksList.getDrinksMap().values().forEach((drink) -> {
            root.addContent(createDrinkXML(drink));
        });

        FileIO.writeXMLDocumentToFile(document, FileIO.DRINKS_LIST_DIRECTORY(), drinksList.getName() + ".xml");
    }

    private Element createDrinkXML(MenuItem drink) {
        Element drinkContainer = new Element("Drink");

        Element nameContainer = new Element("Name");
        nameContainer.addContent(drink.getName());
        drinkContainer.addContent(nameContainer);

        Element priceContainer = new Element("Price");
        priceContainer.addContent(String.valueOf(drink.getPrice()));
        drinkContainer.addContent(priceContainer);
        return drinkContainer;
    }

    @Override
    public Menu interpret(Element root) {
        Menu drinksList = new Menu();
        drinksList.setName(root.getAttributeValue("Name"));
        for (Element drinkElement : root.getChildren("Drink")) {
            String name = drinkElement.getChildText("Name");
            String price = drinkElement.getChildText("Price");
            Double d_Price = Double.valueOf(price);
            MenuItem drink = new MenuItem(d_Price, name);
            drinksList.add(drink);
        }
        return drinksList;
    }

}
