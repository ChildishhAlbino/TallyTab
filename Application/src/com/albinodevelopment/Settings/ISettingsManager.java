/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public interface ISettingsManager {

    public enum settingsList {
        DrinksListDirectory,
        DrinksListInterpreter,
    }

    Setting getSetting(settingsList setting);
    
    HashMap<settingsList, Setting> getSettings();
}
