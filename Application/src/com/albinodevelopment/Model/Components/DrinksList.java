/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.IBuildable;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class DrinksList implements Serializable, IBuildable {

    private String name;

    private transient StringBuilder stringBuilder;

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
        //ConnorLogger.Log(drink.GetName(), ConnorLogger.PriorityLevel.Low);
        return drink;
    }

    public void add(Drink drink) {
        drinks.put(drink.getName(), drink);
    }

    public void remove(Drink drink) {
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
}
