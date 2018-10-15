/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ISettingsManager.settingsList;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class ApplicationSettingsXMLInterpreter {

    public static ApplicationSettings interpret(String filePath) {
        return null;
    }

    public static void save(ISettingsManager applicationSettings) {
        Document document = new Document();
        Element root = new Element("Settings");
        root.setAttribute("AppVersion", "1");
        document.setRootElement(root);
        for (Setting setting : applicationSettings.getSettings().values()) {
            ConnorLogger.log("XML-ing the " + setting.XMLCode + "Setting.", ConnorLogger.PriorityLevel.Low);
            Element element = new Element(setting.getXMLCode());
            element.setText(setting.value.toString());
            root.addContent(element);
        }

        FileIO.writeXMLDocumentToFile(document, FileIO.APPLICATION_DIRECTORY(), "ApplicationSettings.xml");
    }

}
