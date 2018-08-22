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
    private String textFileDirectory = System.getenv("Appdata") + "\\TallyTab";

    @Override
    public IDrinksListInterpreter GetDrinksListInterpreter() {
        return drinksListInterpreter;
    }

    public static ISettingsManager GetInstance() {
        if (instance == null) {
            //check for serialized version first
            instance = new ApplicationSettings();
        }
        return instance;
    }

    @Override
    public String GetTextFileDirectory() {
        return textFileDirectory;
    }

    @Override
    public void setTextFileDirectory(String newDirectory) {
        textFileDirectory = newDirectory + "\\TallyTab";
    }
}
