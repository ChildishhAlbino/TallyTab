/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.Function;
import javafx.scene.control.Button;

/**
 *
 * @author conno
 */
public interface IView {

    void createDrinkGUIElements(Drink drink);

    String GetDrinkNameFromGUI(Button button);

    void New();

    void Open();

    void Save();

    void TabClosed();
    
    void generateFunctionGUI(Function function);
    
    void closeNewFunctionWindow();

}
