/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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
        try {
            Document document = new Document();
            Element root = new Element("DrinksList");
            root.setAttribute("Version", "1");
            root.setAttribute("Name", drinksList.getName());
            document.setRootElement(root);
            drinksList.getDrinksList().values().forEach((drink) -> {
                root.addContent(createDrinkXML(drink));
            });
            
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
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
