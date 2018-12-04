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
public class DrinksTab implements Serializable, XMLable {

    private final DrinksList drinksList;
    private final HashMap<Drink, Integer> count;
    private double limit;
    private double currentValue;
    private double percentUsed;

    public DrinksTab(DrinksList drinksList, double Limit) {
        this.drinksList = drinksList;
        this.limit = Limit;
        this.count = new HashMap<>();
        init();
    }

    public DrinksTab(DrinksList drinksList, double Limit, HashMap<Drink, Integer> count) {
        this.drinksList = drinksList;
        this.limit = Limit;
        this.count = count;
        CheckValues();
    }

    public void CheckValues() {
        currentValue = CalculateCost();
        CalculatePercentUsed();
    }

    public void changeDrinkAmount(int delta, Drink drink) {
        ConnorLogger.log("Attempting to change value of " + drink.getName(), ConnorLogger.PriorityLevel.Low);
        int currentAmount = count.get(drink);
        int newAmount = currentAmount + delta;
        Double potentialValue = currentValue + getPriceForAmount(drink, delta);

        if (potentialValue <= limit) {
            count.put(drink, newAmount);
        } else {
            ConnorLogger.log("Drink would've put you over the limit mate!", ConnorLogger.PriorityLevel.Medium);
        }
        CheckValues();
    }

    public int GetCount(Drink drink) {
        return count.get(drink);
    }

    private Double getPriceForAmount(Drink drink, int amount) {
        Double price = round((drink.getPrice() * amount), 2);
        ConnorLogger.log(drink.getName() + ": " + String.valueOf(price), ConnorLogger.PriorityLevel.Zero);
        return price;
    }

    private Double CalculateCost() throws NumberFormatException {
        double cost = Double.NaN;
        for (int i = 0; i < drinksList.GetListSize(); i++) {
            Drink drink = drinksList.GetDrink(i);
            if (Double.isNaN(cost)) {
                cost = getDrinkSubtotal(drink);
            } else {
                cost += getDrinkSubtotal(drink);
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

    public double getDrinkSubtotal(Drink drink) {
        return getPriceForAmount(drink, GetCount(drink));
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

    public void ChangeLimit(double limit) {
        if (limit > currentValue) {
            this.limit = limit;
            ConnorLogger.log("Limit Changed.", ConnorLogger.PriorityLevel.Zero);
        } else {
            ConnorLogger.log("New limit was below current value. Cannot change.", ConnorLogger.PriorityLevel.Zero);
        }
    }

    public DrinksList getDrinksList() {
        return drinksList;
    }

    public void init() {
        drinksList.getDrinksMap().values().forEach((drink) -> {
            count.put(drink, 0);
        });
    }

    public MenuItemContainer getDrinksTabItem(Drink drink) {
        MenuItemContainer drinksTabItem = new MenuItemContainer(drink, GetCount(drink), getDrinkSubtotal(drink));
        return drinksTabItem;
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
        Element ret = drinksList.toXML();
        for (int i = 0; i < drinksList.GetListSize(); i++) {
            Element currDrink = ret.getChildren("Drink").get(i);
            Element count = new Element("Count");
            count.addContent(String.valueOf(GetCount(drinksList.GetDrink(i))));
            currDrink.addContent(count);
        }

        return ret;
    }

}
