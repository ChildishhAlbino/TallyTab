/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Model.Components.Drink;
import javafx.scene.control.Button;

/**
 *
 * @author conno
 */
public interface IView {

    void CreateDrinkGUI(Drink drink);

    void Refresh();
    
    String GetDrinkNameFromGUI(Button button);
    
}   
