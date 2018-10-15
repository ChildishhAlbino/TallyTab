/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
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

    private final transient XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
    private final transient SAXBuilder saxBuilder = new SAXBuilder();

    @Override
    public DrinksList interpret(String directory) {
        try {
            DrinksList drinksList = new DrinksList();
            Document document = saxBuilder.build(new File(directory));
            Element root = document.getRootElement();

            drinksList.setName(root.getAttributeValue("Name"));
            for (Element drinkElement : root.getChildren("Drink")) {
                String name = drinkElement.getChildText("Name");
                String price = drinkElement.getChildText("Price");
                Double d_Price = Double.valueOf(price);
                Drink drink = new Drink(d_Price, name);
                drinksList.add(drink);
            }
            return drinksList;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLDrinksListInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void save(DrinksList drinksList) {
        try {
            Document document = new Document();
            Element root = new Element("DrinksList");
            root.setAttribute("Version", "1");
            root.setAttribute("Name", drinksList.getName());
            document.setRootElement(root);
            drinksList.getDrinksList().values().forEach((drink) -> {
                root.addContent(createDrinkXML(drink));
            });

            String fileName = System.getProperty("file.separator") + drinksList.getName() + ".xml";
            xmlOutputter.output(document, new FileOutputStream(FileIO.DRINKS_LIST_DIRECTORY() + fileName));
        } catch (IOException ex) {
            Logger.getLogger(XMLDrinksListInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Element createDrinkXML(Drink drink) {
        Element drinkContainer = new Element("Drink");

        Element nameContainer = new Element("Name");
        nameContainer.addContent(drink.getName());
        drinkContainer.addContent(nameContainer);

        Element priceContainer = new Element("Price");
        priceContainer.addContent(String.valueOf(drink.getPrice()));
        drinkContainer.addContent(priceContainer);
        return drinkContainer;
    }

}
