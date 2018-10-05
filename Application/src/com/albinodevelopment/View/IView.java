/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.Functions.Function;
import javafx.scene.control.Button;

/**
 *
 * @author conno
 */
public interface IView {

    void newFunction();

    void open();

    void save();

    void closeTab(String title);

    //void newTab(Function function);
    void updateTab(Function function);

    void closeNewFunctionWindow();

}
