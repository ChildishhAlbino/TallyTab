/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author conno
 */
public class FileIO {

//    public static String openDirectoryWindow(String directory) {
//        String s = null;
//        JFileChooser jFileChooser = new JFileChooser(directory);
//        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int response = jFileChooser.showOpenDialog(null);
//        if (response == JFileChooser.APPROVE_OPTION) {
//            s = jFileChooser.getSelectedFile().toString();
//        } else {
//            ConnorLogger.log("ERROR: Open file operation was cancelled.", ConnorLogger.PriorityLevel.Low);
//        }
//        return s;
//    }
//
//    public static String openDirectoryWindow() {
//        return FileIO.openDirectoryWindow("");
//    }
//
//    public static String openFileExplorer(String directory) {
//        String s = null;
//        JFileChooser jFileChooser = new JFileChooser(directory);
//        jFileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
//        int response = jFileChooser.showOpenDialog(null);
//        if (response == JFileChooser.APPROVE_OPTION) {
//            s = jFileChooser.getSelectedFile().toString();
//        } else {
//            ConnorLogger.log("ERROR: Open file operation was cancelled.", ConnorLogger.PriorityLevel.Low);
//        }
//        return s;
//    }

    public static String APPLICATION_DIRECTORY() {
        String s = ApplicationSettings.getInstance().getSetting(ISettingsManager.settingsList.SerializedDirectory).getValue().toString();
        return s;
    }

    public static String DRINKS_LIST_DIRECTORY() {
        String s = APPLICATION_DIRECTORY();
        s += System.getProperty("file.separator") + "DrinksLists";
        return s;
    }
    
    public static String openFileExplorer(String directory){
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

}
