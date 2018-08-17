/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Model.Components.Drink;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

/**
 *
 * @author conno
 */
public interface IView {

    void CreateDrinkGUIElements(Drink drink);

    void Refresh();

    String GetDrinkNameFromGUI(Button button);

    void New();

    void Open();

    void Save();

    void TabClosed();
    
}
