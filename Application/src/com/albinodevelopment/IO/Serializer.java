/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author conno
 * @param <T>
 */
public class Serializer<T> {

    public void serialize(T object, String fileOutputDirectory, String fileName) {
        FileOutputStream fileOut;
        new File(fileOutputDirectory).mkdir();
        try {
            fileOut = new FileOutputStream(fileOutputDirectory + "\\" + fileName + ".ser");
            try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(object);
                out.close();
            }
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void serializeApplicationSettings() {
        ApplicationSettings applicationSettings
                = (ApplicationSettings) ApplicationSettings.GetInstance();
        SerializerDeserializerFactory.getSerializer(
                com.albinodevelopment.Settings.ApplicationSettings.class)
                .serialize(applicationSettings,
                        applicationSettings.getSetting(ISettingsManager.settingsList.SerializedDirectory).getValue().toString(),
                        applicationSettings.toString());
    }

}
