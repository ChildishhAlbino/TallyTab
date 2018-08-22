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
public interface ISettingsManager {

    public enum settingsList {
        TextFileDirectory,
        DrinksListInterpreter,
    }

    Setting getSetting(settingsList setting);
}
