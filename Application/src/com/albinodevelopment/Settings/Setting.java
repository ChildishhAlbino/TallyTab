/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Components.Interpreter.TextFileDrinksListInterpreter;

/**
 *
 * @author conno
 * @param <U>
 */
public abstract class Setting<U> implements ISetting<U> {

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

    public static class TextFileDirectorySetting extends Setting<String> {

        public TextFileDirectorySetting() {
            defaultValue = System.getenv("Appdata") + "\\TallyTab";
            setToDefault();
        }

        // static class that holds the value of the Text File Directory
        @Override
        public boolean change(String value) {
            if (value != null) {
                // move files from oldDirectory to newDirectory
                this.value = value + "\\TallyTab";
                return true;
            } else {
                return false;
            }
        }
    }

    public static class DrinksListInterpreterSetting extends Setting<IDrinksListInterpreter> {

        public DrinksListInterpreterSetting() {
            defaultValue = new TextFileDrinksListInterpreter();
        }

        @Override
        public boolean change(IDrinksListInterpreter value) {
            if (value != null) {
                // move files from oldDirectory to newDirectory
                this.value = value;
                return true;
            } else {
                return false;
            }
        }

    }
}
