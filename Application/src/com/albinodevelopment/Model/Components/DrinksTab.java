/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Logging.ConnorLogger;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class DrinksTab implements Serializable {

    private final DrinksList drinksList;
    private final HashMap<Drink, Integer> count = new HashMap<>();
    private double limit;
    private double currentValue;
    private double percentUsed;

    public DrinksTab(DrinksList drinksList, double Limit) {
        this.drinksList = drinksList;
        this.limit = Limit;
    }

    public void CheckValues() {
        currentValue = CalculateCost();
        CalculatePercentUsed();
    }

    public void changeDrinkAmount(int delta, Drink drink) {
        ConnorLogger.Log("Attempting to change value of " + drink.GetName(), ConnorLogger.PriorityLevel.Low);
        int currentAmount = count.get(drink);
        int newAmount = currentAmount + delta;
        Double potentialValue = currentValue + getPriceForAmount(drink, delta);

        if (potentialValue <= limit) {
            count.put(drink, newAmount);
        } else {
            ConnorLogger.Log("Drink would've put you over the limit mate!", ConnorLogger.PriorityLevel.Medium);
        }
        CheckValues();
    }

    public int GetCount(Drink drink) {
        return count.get(drink);
    }

    private Double getPriceForAmount(Drink drink, int amount) {
        Double price = drink.GetPrice() * amount;
        ConnorLogger.Log(drink.GetName() + ": " + String.valueOf(price), ConnorLogger.PriorityLevel.Zero);
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
            ConnorLogger.Log("ERROR: Cost was NaN.", ConnorLogger.PriorityLevel.High);
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
            percentUsed = currentValue / limit;
        } else {
            percentUsed = 0;
        }
    }

    public double GetPercentUsed() {
        return percentUsed;
    }

    public double GetCurrentValue() {
        return currentValue;
    }

    public double GetLimit() {
        return limit;
    }

    public void ChangeLimit(double limit) {
        if (limit > currentValue) {
            this.limit = limit;
            ConnorLogger.Log("Limit Changed.", ConnorLogger.PriorityLevel.Zero);
        } else {
            ConnorLogger.Log("New limit was below current value. Cannot change.", ConnorLogger.PriorityLevel.Zero);
        }
    }

    public DrinksList getDrinksList() {
        return drinksList;
    }

    public void init() {
        drinksList.getDrinksList().values().forEach((drink) -> {
            count.put(drink, 0);
        });
    }

    public DrinksTabItem getDrinksTabItem(Drink drink) {
        DrinksTabItem drinksTabItem = new DrinksTabItem(drink, GetCount(drink), getDrinkSubtotal(drink));
        return drinksTabItem;
    }

}
