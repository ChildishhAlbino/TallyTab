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
public class Item implements i_Item {

    private final double price;
    private final String name;

    public Item(double price, String name) {
        this.name = name;
        this.price = price;
    }
}
