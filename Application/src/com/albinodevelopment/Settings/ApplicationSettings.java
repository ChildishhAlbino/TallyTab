/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;

/**
 *
 * @author conno
 */
public class ApplicationSettings implements ISettingsManager {

    private static ISettingsManager instance;
    private IDrinksListInterpreter drinksListInterpreter;

    @Override
    public IDrinksListInterpreter GetDrinksListInterpreter() {
        return drinksListInterpreter;
    }

    public static ISettingsManager GetInstance() {
        if (instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }
}
