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
public class Drink extends Item implements Serializable {

    public Drink(double price, String name) {
        super(price, name);
    }

}
