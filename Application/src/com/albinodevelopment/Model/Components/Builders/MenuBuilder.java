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
    private Menu menu;
    private IDrinksListInterpreter drinksListInterpreter;

    @Override
    public void create() {
        menu = new Menu();
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
                menu = temp;
                ConnorLogger.log("Drinks List opened correctly.", ConnorLogger.PriorityLevel.Low);
            }
        }
    }

    @Override
    public boolean save() {
        checkInterpreter();
        if (menu.GetListSize() > 0) {
            ConnorLogger.log("Saving menu to file.", ConnorLogger.PriorityLevel.Low);
            drinksListInterpreter.save(menu);
            clear();
            return true;
        }
        return false;
    }

    @Override
    public Menu get() {
        if (menu == null) {
            create();
        }
        return menu;
    }

    private void checkInterpreter() {
        drinksListInterpreter = (IDrinksListInterpreter) ApplicationSettings.getInstance()
                .getSetting(ISettingsManager.settingsList.DrinksListInterpreter).getValue();
    }

    @Override
    public void clear() {
        menu = null;
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
        Menu menu = drinksListInterpreter.interpret(path);
        return menu;
    }

    public void changeName(String text) {
        menu.setName(text);
    }

}
