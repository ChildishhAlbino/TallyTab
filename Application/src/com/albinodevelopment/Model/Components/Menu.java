/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Model.Components.Builders.IBuildable;
import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import java.util.TreeMap;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class Menu implements Serializable, IBuildable, XMLable {

    private String name;

    private transient StringBuilder stringBuilder;

    private final TreeMap<String, MenuItem> drinks = new TreeMap<>((String o1, String o2) -> o1.compareTo(o2));
    
    public Menu(){
        name = "New Menu";
    }
    
    public TreeMap<String, MenuItem> getDrinksMap() {
        return drinks;
    }

    public MenuItem GetDrink(String name) {
        return drinks.get(name);
    }

    public int GetListSize() {
        return drinks.size();
    }

    public MenuItem GetDrink(int index) {
        MenuItem drink = (MenuItem) drinks.values().toArray()[index];
        //ConnorLogger.Log(drink.GetName(), ConnorLogger.PriorityLevel.Low);
        return drink;
    }

    public void add(MenuItem drink) {
        drinks.put(drink.getName(), drink);
    }

    public void remove(MenuItem drink) {
        drinks.remove(drink.getName());
    }

    @Override
    public String toString() {
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        stringBuilder.delete(0, stringBuilder.length());
        drinks.values().forEach((drink) -> {
            stringBuilder.append(drink.toString()).append("\n");
        });
        String s = stringBuilder.toString();
        return s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Element toXML() {
        Element root = new Element("DrinksList");
        root.setAttribute("Version", "1");
        root.setAttribute("Name", this.name);
        drinks.values().forEach((drink) -> {
            root.addContent(drink.toXML());
        });
        
        return root;
    }
}
