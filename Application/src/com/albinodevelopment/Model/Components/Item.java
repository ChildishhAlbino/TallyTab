/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import java.io.Serializable;

/**
 *
 * @author conno
 */
public abstract class Item implements i_Item, Serializable {

    protected double price;
    protected String name;

    public Item(double price, String name) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return DrinksTab.round(price, 4);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String s = "Drink: " + name + " Price: $" + price;
        return s;
    }
}
