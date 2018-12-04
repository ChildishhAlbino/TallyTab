/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Builders;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;

/**
 *
 * @author conno
 */
public class MenuBuilder implements IComponentManager<Menu> {

    private static MenuBuilder instance;
    private Menu drinksList;
    private IDrinksListInterpreter drinksListInterpreter;

    @Override
    public void create() {
        drinksList = new Menu();
    }

    @Override
    public void open() {
        String directory = FileIO.openFileExplorer(
                ApplicationSettings.getInstance()
                        .getSetting(ISettingsManager.settingsList.DrinksListDirectory)
                        .getValue().toString() + "\\DrinksLists");
        if (directory != null) {
            ConnorLogger.log(directory, ConnorLogger.PriorityLevel.Low);
            checkInterpreter();

            Menu temp = drinksListInterpreter.interpret(directory);
            if (temp != null) {
                drinksList = temp;
                ConnorLogger.log("Drinks List opened correctly.", ConnorLogger.PriorityLevel.Low);
            }
        }
    }

    @Override
    public void save() {
        checkInterpreter();
        drinksListInterpreter.save(drinksList);
        clear();
    }

    @Override
    public Menu get() {
        return drinksList;
    }

    private void checkInterpreter() {
        drinksListInterpreter = (IDrinksListInterpreter) ApplicationSettings.getInstance()
                .getSetting(ISettingsManager.settingsList.DrinksListInterpreter).getValue();
    }

    @Override
    public void clear() {
        drinksList = null;
    }

    public static MenuBuilder getInstance() {
        if (instance == null) {
            instance = new MenuBuilder();
        }
        return instance;
    }

    @Override
    public boolean validate(String path) {
        checkInterpreter();
        Menu temp = drinksListInterpreter.interpret(path);
        return temp != null;
    }

    @Override
    public Menu openAndGet(String path) {
        checkInterpreter();
        Menu dl = drinksListInterpreter.interpret(path);
        return dl;
    }

}
