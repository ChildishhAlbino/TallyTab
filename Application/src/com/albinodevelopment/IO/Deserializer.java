/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import com.albinodevelopment.Logging.ConnorLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author conno
 * @param <T>
 */
public class Deserializer<T> {

    private final String fileDirectory;

    public Deserializer() {
        fileDirectory = FileIO.readDirectoryFile();
    }

    public T deserializeFromFilePath(String filePath) {
        try {
            FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            T t = (T) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return t;
        } catch (FileNotFoundException ex) {
            ConnorLogger.log("ERROR: File: " + filePath + " not found - " + ex.getLocalizedMessage(), ConnorLogger.PriorityLevel.Medium);
        } catch (IOException | ClassNotFoundException ex) {
            ConnorLogger.log("ERROR IO or ClassNotFound exception - " + ex.getLocalizedMessage(), ConnorLogger.PriorityLevel.Medium);
        }
        return null;
    }

    public T deserializeFromFileName(String fileName) {
        return deserializeFromFilePath(fileDirectory + "\\" + fileName);
    }

}
