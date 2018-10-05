/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.IO.SerializerDeserializerFactory;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import java.io.Serializable;

/**
 *
 * @author conno
 */
public class SerializedDrinksListInterpreter implements IDrinksListInterpreter, Serializable {

    @Override
    public DrinksList Interpret(String directory) {
        DrinksList drinksList = SerializerDeserializerFactory
                .getDeserializer(com.albinodevelopment.Model.Components.DrinksList.class)
                .DeserializeFromFilePath(directory);
        if (drinksList != null) {
            return drinksList;
        } else {
            return null;
        }
    }

    @Override
    public void Save(DrinksList drinksList) {
        SerializerDeserializerFactory.getSerializer(
                com.albinodevelopment.Model.Components.DrinksList.class)
                .serialize(drinksList,
                        ApplicationSettings.GetInstance()
                                .getSetting(ISettingsManager.settingsList.SerializedDirectory).getValue().toString() + "\\DrinksList", drinksList.getName());
    }
}
