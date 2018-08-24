/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

/**
 *
 * @author conno
 */
public abstract class Item implements i_Item {

    protected final double price;
    protected final String name;

    public Item(double price, String name) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double GetPrice() {
        return price;
    }

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public String toString() {
        String s = "Drink: " + name + " Price: $" + price;
        return s;
    }
}
