/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.MenuItemContainer;

/**
 *
 * @author conno
 */
public abstract class MenuItemTemplate extends Template<MenuItemContainer> {
    
    protected FunctionTemplate tabContent;

    public void setTabContent(FunctionTemplate tabContent) {
        this.tabContent = tabContent;
    }

}
