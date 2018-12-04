/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.MenuItemContainer;

/**
 *
 * @author conno
 */
public abstract class MenuItemTemplate extends Template implements ITemplate<MenuItemContainer> {

    protected Drink drink;
    protected FunctionTemplate tabContent;

    public void setTabContent(FunctionTemplate tabContent) {
        this.tabContent = tabContent;
    }

}
