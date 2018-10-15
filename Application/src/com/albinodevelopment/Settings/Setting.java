/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Interpreter.DatabaseDrinksListInterpreter;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Components.Interpreter.XMLDrinksListInterpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author conno
 * @param <U>
 */
public abstract class Setting<U> implements ISetting<U>, Serializable {

    protected U value;
    protected U defaultValue;

    @Override
    public U getValue() {
        return value;
    }

    @Override
    public void setToDefault() {
        value = defaultValue;
    }

    public String getXMLCode() {
        return "Code not set";
    }

    public String getXMLValue() {
        return "Value was not set by subclass.";
    }

    public static String getDrinksListDirectoryTag() {
        return "DrinksListDirectory";
    }

    public static String getDrinksListInterpreterTag() {
        return "DrinksListInterpreter";
    }

    public static class DrinksListDirectorySetting extends Setting<String> {

        public DrinksListDirectorySetting() {
            defaultValue = getLocalAppFolder();
            setToDefault();
            //restructure();
        }

        // static class that holds the value of the Text File Directory
        @Override
        public boolean change(String value) {
            if (value != null) {
                // move files from oldDirectory to newDirectory
                this.value = value;
                if (this.value.contains("TallyTab") == false) {
                    this.value += System.getProperty("file.separator") + "TallyTab";
                }
                restructure();
                return true;
            } else {
                return false;
            }
        }

        private void writeToFile() {
            File file = new File("userDirectory.txt");
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(value);
                ConnorLogger.log("NOTE: userDirectory.txt written with value = " + value, ConnorLogger.PriorityLevel.Low);
                printWriter.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void setupDirectory() {
            new File(value).mkdir();
            new File(value + System.getProperty("file.separator") + "DrinksLists").mkdir();
        }

        private void restructure() {
            writeToFile();
            setupDirectory();
        }

        private String getLocalAppFolder() {
            String returnVar = null;
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                returnVar = System.getenv("Appdata") + "\\TallyTab";
            } else {
                returnVar = System.getProperty("user.home");
                if (os.contains("Mac")) {
                    returnVar += "/Documents/TallyTab";
                }
            }
            return returnVar;
        }

        @Override
        public String getXMLValue() {
            return value;
        }

        public String getXMLCode() {
            return getDrinksListDirectoryTag();
        }

    }

    public static class DrinksListInterpreterSetting extends Setting<IDrinksListInterpreter> {

        public DrinksListInterpreterSetting() {
            defaultValue = new XMLDrinksListInterpreter();
            setToDefault();
        }

        @Override
        public boolean change(IDrinksListInterpreter value) {
            if (value != null) {
                this.value = value;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String getXMLValue() {
            if (value instanceof XMLDrinksListInterpreter) {
                return "XML";
            }

            if (value instanceof DatabaseDrinksListInterpreter) {
                return "DB";
            }

            return super.getXMLValue();
        }

        public String getXMLCode() {
            return getDrinksListInterpreterTag();
        }

    }
}
