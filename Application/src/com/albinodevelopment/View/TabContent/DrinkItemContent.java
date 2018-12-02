/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksTabContainer;

/**
 *
 * @author conno
 */
public abstract class DrinkItemContent extends Content implements IContent<DrinksTabContainer> {

    protected Drink drink;
    protected FunctionTabContent tabContent;

    public void setTabContent(FunctionTabContent tabContent) {
        this.tabContent = tabContent;
    }

}
