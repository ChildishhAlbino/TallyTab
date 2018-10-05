/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksTabItem;

/**
 *
 * @author conno
 */
public abstract class DrinkItemContent extends Content implements IContent<DrinksTabItem> {
    protected Drink drink;
    protected TabContent tabContent;

    public void setTabContent(TabContent tabContent) {
        this.tabContent = tabContent;
    }

}
