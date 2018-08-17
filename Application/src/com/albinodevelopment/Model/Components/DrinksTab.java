/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Logging.PriorityLogger;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class DrinksTab {

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

    public void Add(int amount, Drink drink) {
        int currentAmount = count.get(drink);
        int totalAmount = currentAmount + amount;
        PriorityLogger.Log(Integer.toString(totalAmount), PriorityLogger.PriorityLevel.Low);
        count.put(drink, totalAmount);
        CheckValues();
    }

    public void Remove(int amount, Drink drink) {
        int currentAmount = count.get(drink);
        int totalAmount = currentAmount - amount;
        PriorityLogger.Log(Integer.toString(totalAmount), PriorityLogger.PriorityLevel.Low);
        count.put(drink, totalAmount);
        CheckValues();
    }

    public int GetCount(Drink drink) {
        return count.get(drink);
    }

    private double CalculateCost() throws NumberFormatException {
        double cost = Double.NaN;
        for (int i = 0; i < drinksList.GetListSize(); i++) {
            Drink drink = drinksList.GetDrink(i);
            cost += (drink.GetPrice() * count.get(drink));
            PriorityLogger.Log(Double.toString(cost), PriorityLogger.PriorityLevel.Low);
        }
        if (Double.isNaN(cost)) {
            PriorityLogger.Log("ERROR: Cost was NaN.", PriorityLogger.PriorityLevel.Zero);
            throw new NumberFormatException();
        } else {
            return cost;
        }
    }

    private void CalculatePercentUsed() {
        if (Double.isFinite(limit)) {
            percentUsed = currentValue / limit;
        } else {
            percentUsed = 0;
        }
        PriorityLogger.Log(Double.toString(percentUsed), PriorityLogger.PriorityLevel.Low);
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
            PriorityLogger.Log("Limit Changed.", PriorityLogger.PriorityLevel.Zero);
        } else {
            PriorityLogger.Log("New limit was below current value. Cannot change.", PriorityLogger.PriorityLevel.Zero);
        }
    }
}
