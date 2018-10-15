/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author conno
 */
public class FileIO {

    private static final SAXBuilder saxBuilder = new SAXBuilder();
    private static final XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

    public static Document getXMLDocumentFromFile(String filePath) {
        try {
            Document document = saxBuilder.build(new File(filePath));
            return document;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean writeXMLDocumentToFile(Document document, String directory, String fileName) {
        try {
            xmlOutputter.output(document, new FileOutputStream(directory + System.getProperty("file.separator") + fileName));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String APPLICATION_DIRECTORY() {
        String s = ApplicationSettings.getInstance().getSetting(ISettingsManager.settingsList.DrinksListDirectory).getValue().toString();
        return s;
    }

    public static String DRINKS_LIST_DIRECTORY() {
        String s = APPLICATION_DIRECTORY();
        s += System.getProperty("file.separator") + "DrinksLists";
        return s;
    }

    public static String openFileExplorer(String directory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        fileChooser.setInitialDirectory(new File(directory));
        File selected = fileChooser.showOpenDialog(new Stage());
        String s = null;
        if (selected != null) {
            s = selected.getAbsolutePath();
        } else {
            ConnorLogger.log("ERROR: File selector was cancelled.", ConnorLogger.PriorityLevel.Zero);
        }
        return s;
    }

    public static String openDirectoryWindow() {
        return openDirectoryWindow(APPLICATION_DIRECTORY());
    }

    public static String openDirectoryWindow(String directory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");
        directoryChooser.setInitialDirectory(new File(directory));
        File selected = directoryChooser.showDialog(new Stage());
        String s = null;
        if (selected != null) {
            s = selected.getAbsolutePath();
        } else {
            ConnorLogger.log("ERROR: Directory selected was cancelled.", ConnorLogger.PriorityLevel.Zero);
        }
        return s;
    }

    public static String readDirectoryFile() {
        File file = new File("userDirectory.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String directory = bufferedReader.readLine();
            if (directory != null) {
                ConnorLogger.log("Directory == " + directory, ConnorLogger.PriorityLevel.Zero);
                return directory + System.getProperty("file.separator");
            } else {
                throw new IOException("The directory was null.");
            }

        } catch (FileNotFoundException ex) {
            ConnorLogger.log("ERROR: File: " + file.getName() + " not found - " + ex.getLocalizedMessage(), ConnorLogger.PriorityLevel.Medium);
        } catch (IOException ex) {
            ConnorLogger.log("ERROR: IOException. " + ex.getLocalizedMessage(), ConnorLogger.PriorityLevel.Medium);
        }

        return null;
    }

}
