/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

/**
 *
 * @author conno
 * @param <U>
 */
public abstract class Setting<U> {

    protected U value;
    protected U defaultValue;

    public enum settingState {
    }

    protected boolean change(U value) {
        return true;
    }

    protected U getValue() {
        return value;
    }

    protected U getDefault() {
        return defaultValue;
    }

    public static class TextFileDirectorySetting extends Setting<String> {

        @Override
        protected boolean change(String value) {
            if (value != null) {
                this.value = value;
                return true;
            } else {
                return false;
            }
        }

    }
}
