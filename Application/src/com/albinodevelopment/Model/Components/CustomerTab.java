/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class CustomerTab implements Serializable, XMLable {

    private final Menu menu;
    private final HashMap<MenuItem, Integer> count;
    private double limit;
    private double currentValue;
    private double percentUsed;

    public CustomerTab(Menu drinksList, double Limit) {
        this.menu = drinksList;
        this.limit = Limit;
        this.count = new HashMap<>();
        init();
    }

    public CustomerTab(Menu menu, double Limit, HashMap<MenuItem, Integer> count) {
        this.menu = menu;
        this.limit = Limit;
        this.count = count;
        init();
        CheckValues();
    }

    public void CheckValues() {
        currentValue = CalculateCost();
        CalculatePercentUsed();
    }

    public void changeItemAmount(int delta, MenuItem item) {
        ConnorLogger.log("Attempting to change value of " + item.getName(), ConnorLogger.PriorityLevel.Low);
        int currentAmount = count.get(item);
        int newAmount = currentAmount + delta;
        Double potentialValue = currentValue + getPriceForAmount(item, delta);

        if (potentialValue <= limit) {
            count.put(item, newAmount);
        } else {
            ConnorLogger.log("Item would've put you over the limit mate!", ConnorLogger.PriorityLevel.Medium);
        }
        CheckValues();
    }

    public int GetCount(MenuItem item) {
        return count.get(item);
    }

    private Double getPriceForAmount(MenuItem item, int amount) {
        Double price = round((item.getPrice() * amount), 2);
        ConnorLogger.log(item.getName() + ": " + String.valueOf(price), ConnorLogger.PriorityLevel.Zero);
        return price;
    }

    private Double CalculateCost() throws NumberFormatException {
        double cost = Double.NaN;
        for (int i = 0; i < menu.GetListSize(); i++) {
            MenuItem drink = menu.GetDrink(i);
            if (Double.isNaN(cost)) {
                cost = getItemSubtotal(drink);
            } else {
                cost += getItemSubtotal(drink);
            }
            //ConnorLogger.Log(String.valueOf(cost), ConnorLogger.PriorityLevel.Low);
        }
        if (Double.isNaN(cost)) {
            ConnorLogger.log("ERROR: Cost was NaN.", ConnorLogger.PriorityLevel.High);
            throw new NumberFormatException();
        } else {
            return cost;
        }
    }

    public double getItemSubtotal(MenuItem item) {
        return getPriceForAmount(item, GetCount(item));
    }

    private void CalculatePercentUsed() {
        if (Double.isFinite(limit)) {
            currentValue = round(currentValue, 4);
            percentUsed = currentValue / limit;
            percentUsed = round(percentUsed, 4);
        } else {
            percentUsed = 0;
        }
    }

    public double GetPercentUsed() {
        return percentUsed;
    }

    public double GetCurrentValue() {
        return round(currentValue, 4);
    }

    public double GetLimit() {
        return limit;
    }

    public boolean ChangeLimit(double limit) {
        if (limit >= currentValue) {
            this.limit = limit;
            ConnorLogger.log("Limit Changed.", ConnorLogger.PriorityLevel.Zero);
            CheckValues();
            return true;
        } else {
            ConnorLogger.log("New limit was below current value. Cannot change.", ConnorLogger.PriorityLevel.Zero);
            return false;
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void init() {
        menu.getMenuMap().values().forEach((item) -> {
            if(count.get(item) == null){
                count.put(item, 0);
            } 
        });
    }

    public MenuItemContainer getMenuItemContainer(MenuItem menuItem) {
        MenuItemContainer menuItemContainer = new MenuItemContainer(menuItem, GetCount(menuItem), getItemSubtotal(menuItem));
        return menuItemContainer;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public Element toXML() {
        Element ret = menu.toXML();
        for (int i = 0; i < menu.GetListSize(); i++) {
            Element currDrink = ret.getChildren("Drink").get(i);
            Element count = new Element("Count");
            count.addContent(String.valueOf(GetCount(menu.GetDrink(i))));
            currDrink.addContent(count);
        }

        return ret;
    }

}
