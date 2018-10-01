/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.Logging.PriorityLogger;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Components.Interpreter.SerializedDrinksListInterpreter;
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
        change(defaultValue);
    }

    public static class SerializedDirectorySetting extends Setting<String> {

        public SerializedDirectorySetting() {
            defaultValue = System.getenv("Appdata") + "\\TallyTab";
            setToDefault();
            WriteToFile();
        }

        // static class that holds the value of the Text File Directory
        @Override
        public boolean change(String value) {
            if (value != null) {
                // move files from oldDirectory to newDirectory
                this.value = value;
                if (this.value.contains("TallyTab") == false) {
                    this.value += "\\TallyTab";
                }
                WriteToFile();
                return true;
            } else {
                return false;
            }
        }

        private void WriteToFile() {
            File file = new File("userDirectory.txt");
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(value);
                PriorityLogger.Log("NOTE: userDirectory.txt written with value = " + value, PriorityLogger.PriorityLevel.Low);
                printWriter.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class DrinksListInterpreterSetting extends Setting<IDrinksListInterpreter> {

        public DrinksListInterpreterSetting() {
            defaultValue = new SerializedDrinksListInterpreter();
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

    }
}
