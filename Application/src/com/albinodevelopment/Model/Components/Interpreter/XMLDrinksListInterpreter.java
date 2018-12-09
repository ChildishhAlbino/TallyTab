/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Model.Components.Item.ItemState;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.Menu;
import java.io.Serializable;
import org.jdom2.*;

/**
 *
 * @author conno
 */
public class XMLDrinksListInterpreter implements IDrinksListInterpreter, Serializable {

    @Override
    public Menu interpret(String directory) {
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
        drinksList.getMenuMap().values().forEach((item) -> {
            root.addContent(item.toXML());
        });

        FileIO.writeXMLDocumentToFile(document, FileIO.DRINKS_LIST_DIRECTORY(), drinksList.getName() + ".xml");
    }

    @Override
    public Menu interpret(Element root) {
        Menu drinksList = new Menu();
        drinksList.setName(root.getAttributeValue("Name"));
        for (Element drinkElement : root.getChildren("Drink")) {
            String name = drinkElement.getChildText("Name");
            String price = drinkElement.getChildText("Price");  
            Double d_Price = Double.valueOf(price);
            MenuItem item = new MenuItem(d_Price, name);
            if(drinkElement.getChild("State") != null){
                ItemState state = (drinkElement.getChildText("State").equals("Locked") ? ItemState.locked : ItemState.open);
                item.setItemState(state);
            }
            drinksList.add(item);
        }
        return drinksList;
    }

}
