/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Model.Components.Builders.IBuildable;
import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class Menu implements Serializable, IBuildable, XMLable {

    private String name;

    private transient StringBuilder stringBuilder;

    private final TreeMap<String, MenuItem> items = new TreeMap<>((String o1, String o2) -> o1.compareToIgnoreCase(o2));

    public Menu() {
        name = "New Menu";
    }

    public TreeMap<String, MenuItem> getMenuMap() {
        return items;
    }

    public MenuItem GetDrink(String name) {
        return items.get(name);
    }

    public int GetListSize() {
        return items.size();
    }

    public MenuItem GetDrink(int index) {
        MenuItem drink = (MenuItem) items.values().toArray()[index];
        //ConnorLogger.Log(drink.GetName(), ConnorLogger.PriorityLevel.Low);
        return drink;
    }

    public void add(MenuItem drink) {
        items.put(drink.getName(), drink);
    }

    public void remove(MenuItem drink) {
        items.remove(drink.getName());
    }

    @Override
    public String toString() {
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        stringBuilder.delete(0, stringBuilder.length());
        items.values().forEach((drink) -> {
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
        items.values().forEach((drink) -> {
            root.addContent(drink.toXML());
        });

        return root;
    }

    public boolean contains(MenuItem item) {

        if (items.containsKey(item.name)) {
            return true;
        } else {
            return false;
        }
    }
}
