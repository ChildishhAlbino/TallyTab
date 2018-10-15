/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.IO.SerializerDeserializerFactory;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class ApplicationSettings implements ISettingsManager, Serializable {

    private final HashMap<settingsList, Setting> settings;
    private static ISettingsManager instance;

    public ApplicationSettings() {
        settings = new HashMap<>();
        setupSettings();
    }

    public static ISettingsManager getInstance() {
    if (instance == null) {
            String fileDirectory = FileIO.readDirectoryFile();
            if (!(fileDirectory == null)) {
                ISettingsManager iSettingsManager = ApplicationSettingsXMLInterpreter.interpret(fileDirectory + "ApplicationSettings.xml");
                if (iSettingsManager != null) {
                    instance = iSettingsManager;
                }
            } else {
                instance = new ApplicationSettings();
            }
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
                case DrinksListDirectory:
                    return new Setting.DrinksListDirectorySetting();
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

    @Override
    public String toString() {
        return "ApplicationSettings";
    }

    @Override
    public HashMap<settingsList, Setting> getSettings() {
        return settings;
    }
}
