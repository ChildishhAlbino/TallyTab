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
public class MenuItemContainer {

    private final MenuItem drink;
    private final int amount;
    private final Double subtotal;

    public MenuItemContainer(MenuItem drink, int amount, Double subtotal) {
        this.drink = drink;
        this.amount = amount;
        this.subtotal = subtotal;
    }

    public MenuItem getDrink() {
        return drink;
    }

    public int getAmountUsed() {
        return amount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

}
