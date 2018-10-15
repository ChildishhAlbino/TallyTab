/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Interpreter.XMLDrinksListInterpreter;
import com.albinodevelopment.Settings.ISettingsManager.settingsList;
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
        ApplicationSettings applicationSettings = new ApplicationSettings();
        
        
        for (Element child : root.getChildren()) {
            if (child.getName().equals(applicationSettings.getSetting(settingsList.DrinksListDirectory).getXMLCode())) {
                ConnorLogger.log("Found the directory setting.", ConnorLogger.PriorityLevel.Low);
                applicationSettings.getSetting(settingsList.DrinksListDirectory).change(child.getText());
            } else if (child.getName().equals(applicationSettings.getSetting(settingsList.DrinksListInterpreter).getXMLCode())) {
                ConnorLogger.log("Found the Interpreter setting.", ConnorLogger.PriorityLevel.Low);
                String code = child.getText();
                switch (code) {
                    case "XML":
                        applicationSettings.getSetting(settingsList.DrinksListInterpreter).change(new XMLDrinksListInterpreter());
                        break;
                    case "DB":
                        applicationSettings.getSetting(settingsList.DrinksListInterpreter).change(new XMLDrinksListInterpreter());
                        break;
                    default:
                        ConnorLogger.log("Interpreter code was not a recognised interpreter - " + code, ConnorLogger.PriorityLevel.Low);
                        break;
                }
            } else {
                ConnorLogger.log("Child was not a recognised setting.", ConnorLogger.PriorityLevel.Low);
            }
        }
        return applicationSettings;
    }

    public static void save(ISettingsManager applicationSettings) {
        Document document = new Document();
        Element root = new Element("Settings");
        root.setAttribute("AppVersion", "1");
        document.setRootElement(root);
        for (Setting setting : applicationSettings.getSettings().values()) {
            ConnorLogger.log("XML-ing the " + setting.XMLCode + "Setting.", ConnorLogger.PriorityLevel.Low);
            Element element = new Element(setting.getXMLCode());
            element.setText(setting.getXMLValue());
            root.addContent(element);
        }

        FileIO.writeXMLDocumentToFile(document, FileIO.APPLICATION_DIRECTORY(), "ApplicationSettings.xml");
    }
    
    
    
}
