/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Functions;

import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import java.io.Serializable;

/**
 *
 * @author conno
 */
public class Function implements Serializable {

    private final String name;
    private final DrinksTab drinksTab;

    public Function(String name, DrinksTab tab) {
        this.name = name;
        this.drinksTab = tab;
        drinksTab.init();
    }

    public String getName() {
        return name;
    }

    public String getLimit() {
        Double d_limit = drinksTab.GetLimit();
        return d_limit.toString();
    }

    public String getCurrentValue() {
        Double d_Current = drinksTab.GetCurrentValue();
        return d_Current.toString();
    }

    public String getPercentUsed() {
        Double d_Percent = drinksTab.GetPercentUsed() * 100;
        d_Percent = DrinksTab.round(d_Percent, 4);
        return d_Percent.toString() + "%";
    }

    public Double getPercentAsDouble() {
        return drinksTab.GetPercentUsed();
    }

    public DrinksList getDrinksList() {
        return drinksTab.getDrinksList();
    }

    public DrinksTab getDrinksTab() {
        return drinksTab;
    }

}
