/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.Model.Components.Drink;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

/**
 *
 * @author conno
 */
public interface ITabContent<T> {
    Parent generateContent(T input);
    //HBox generateDrinkGUI(Drink drink)
    
}
