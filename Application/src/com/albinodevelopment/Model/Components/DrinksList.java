/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Logging.PriorityLogger;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class DrinksList implements Serializable{

    private final HashMap<String, Drink> drinks = new HashMap<>();

    public HashMap<String, Drink> getDrinksList() {
        return drinks;
    }

    public Drink GetDrink(String name) {
        return drinks.get(name);
    }

    public int GetListSize() {
        return drinks.size();
    }

    public Drink GetDrink(int index) {
        Drink drink = (Drink) drinks.values().toArray()[index];
        PriorityLogger.Log(drink.GetName(), PriorityLogger.PriorityLevel.Low);
        return drink;
    }
}
