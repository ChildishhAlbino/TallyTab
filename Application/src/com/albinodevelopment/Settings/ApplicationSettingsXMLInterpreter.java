/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Interpreter.DatabaseDrinksListInterpreter;
import com.albinodevelopment.Model.Components.Interpreter.XMLDrinksListInterpreter;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class ApplicationSettingsXMLInterpreter {

    public static ApplicationSettings interpret(String filePath) {
        Document document = FileIO.getXMLDocumentFromFile(filePath);
        Element root = document.getRootElement();
        Setting.DrinksListDirectorySetting drinksListDirectorySetting = null;
        Setting.DrinksListInterpreterSetting drinksListInterpreterSetting = null;
        for (Element child : root.getChildren()) {
            if (child.getName().equals(Setting.getDrinksListDirectoryTag())) {
                drinksListDirectorySetting = createDrinksListDirectorySetting(child);
            } else if (child.getName().equals(Setting.getDrinksListInterpreterTag())) {
                drinksListInterpreterSetting = createDrinksListInterpreterSetting(child);
            } else {
                ConnorLogger.log("Child was not a recognised setting.", ConnorLogger.PriorityLevel.Low);
            }
        }
        return new ApplicationSettings(drinksListDirectorySetting, drinksListInterpreterSetting);
    }

    public static void save(ISettingsManager applicationSettings) {
        Document document = new Document();
        Element root = new Element("Settings");
        root.setAttribute("AppVersion", "1");
        document.setRootElement(root);
        for (Setting setting : applicationSettings.getSettings().values()) {
            ConnorLogger.log("XML-ing the " + setting.getXMLCode() + "Setting.", ConnorLogger.PriorityLevel.Low);
            Element element = new Element(setting.getXMLCode());
            element.setText(setting.getXMLValue());
            root.addContent(element);
        }

        FileIO.writeXMLDocumentToFile(document, FileIO.APPLICATION_DIRECTORY(), "ApplicationSettings.xml");
    }

    public static Setting.DrinksListDirectorySetting createDrinksListDirectorySetting(Element element) {
        ConnorLogger.log("Found the directory setting.", ConnorLogger.PriorityLevel.Low);
        Setting.DrinksListDirectorySetting drinksListDirectorySetting = new Setting.DrinksListDirectorySetting();
        drinksListDirectorySetting.change(element.getText());
        return drinksListDirectorySetting;
    }

    public static Setting.DrinksListInterpreterSetting createDrinksListInterpreterSetting(Element element) {
        ConnorLogger.log("Found the Interpreter setting.", ConnorLogger.PriorityLevel.Low);
        Setting.DrinksListInterpreterSetting drinksListInterpreterSetting = new Setting.DrinksListInterpreterSetting();
        String code = element.getText();
        switch (code) {
            case "XML":
                drinksListInterpreterSetting.change(new XMLDrinksListInterpreter());
                break;
            case "DB":
                drinksListInterpreterSetting.change(new DatabaseDrinksListInterpreter());
                break;
            default:
                ConnorLogger.log("Interpreter code was not a recognised interpreter - " + code, ConnorLogger.PriorityLevel.Low);
                break;
        }

        return drinksListInterpreterSetting;
    }

}
