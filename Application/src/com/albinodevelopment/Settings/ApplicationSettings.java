/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import java.util.HashMap;

/**
 *
 * @author conno
 */
public class ApplicationSettings implements ISettingsManager {

    private HashMap<settingsList, Setting> settings;
    private static ISettingsManager instance;

    public ApplicationSettings() {
        settings = new HashMap<>();
        setupSettings();
    }

    public static ISettingsManager GetInstance() {
        if (instance == null) {
            //check for serialized version first
            instance = new ApplicationSettings();
        }
        return instance;
    }

    private Setting getSettingFromEnum(settingsList setting) {
        if (null == setting) {
            return null;
        } else {
            switch (setting) {
                case DrinksListInterpreter:
                    return new Setting.DrinksListInterpreterSetting();
                case TextFileDirectory:
                    return new Setting.TextFileDirectorySetting();
                default:
                    return null;
            }
        }
    }

    @Override
    public Setting getSetting(settingsList setting) {
        return settings.get(setting);
    }

    private void setupSettings() {
        if (settings.isEmpty()) {
            for (settingsList value : settingsList.values()) {
                settings.put(value, getSettingFromEnum(value));
            }
        }
    }
}
