/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Builders;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.PriorityLogger;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;

/**
 *
 * @author conno
 */
public class DrinksListBuilder implements IComponentManager<DrinksList> {

    private static DrinksListBuilder instance;
    private DrinksList drinksList;
    private IDrinksListInterpreter drinksListInterpreter;

    @Override
    public void create() {
        drinksList = new DrinksList();
    }

    @Override
    public void open() {
        String directory = FileIO.openFileExplorer(
                ApplicationSettings.GetInstance()
                        .getSetting(ISettingsManager.settingsList.SerializedDirectory)
                        .getValue().toString() + "\\DrinksList");
        if (directory != null) {
            PriorityLogger.Log(directory, PriorityLogger.PriorityLevel.Low);
            checkInterpreter();

            DrinksList temp = drinksListInterpreter.Interpret(directory);
            if (temp != null) {
                drinksList = temp;
                PriorityLogger.Log("Drinks List opened correctly.", PriorityLogger.PriorityLevel.Low);
            }
        }
    }

    @Override
    public void save() {
        checkInterpreter();
        drinksListInterpreter.Save(drinksList);
        clear();
    }

    @Override
    public DrinksList get() {
        return drinksList;
    }

    private void checkInterpreter() {
        drinksListInterpreter = (IDrinksListInterpreter) ApplicationSettings.GetInstance()
                .getSetting(ISettingsManager.settingsList.DrinksListInterpreter).getValue();
    }

    @Override
    public void clear() {
        drinksList = null;
    }

    public static DrinksListBuilder getInstance() {
        if (instance == null) {
            instance = new DrinksListBuilder();
        }
        return instance;
    }

    @Override
    public boolean validate(String path) {
        checkInterpreter();
        DrinksList temp = drinksListInterpreter.Interpret(path);
        return temp != null;
    }

    @Override
    public DrinksList openAndGet(String path) {
        checkInterpreter();
        DrinksList dl = drinksListInterpreter.Interpret(path);
        return dl;
    }

}
