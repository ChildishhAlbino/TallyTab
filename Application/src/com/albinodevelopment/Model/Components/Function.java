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
public class Function implements Serializable{

    private final String name;
    private final DrinksTab tab;

    public Function(String name, DrinksTab tab) {
        this.name = name;
        this.tab = tab;
    }

    public String GetName() {
        return name;
    }

}
