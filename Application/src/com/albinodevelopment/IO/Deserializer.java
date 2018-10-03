/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import com.albinodevelopment.Logging.PriorityLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author conno
 * @param <T>
 */
public class Deserializer<T> {

    private final String fileDirectory;

    public Deserializer() {
        fileDirectory = readDirectoryFile();
    }

    public T DeserializeFromFilePath(String filePath) {
        try {
            FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            T t = (T) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return t;
        } catch (FileNotFoundException ex) {
            PriorityLogger.Log("ERROR: File: " + filePath + " not found - " + ex.getLocalizedMessage(), PriorityLogger.PriorityLevel.Medium);
        } catch (IOException | ClassNotFoundException ex) {
            PriorityLogger.Log("ERROR IO or ClassNotFound exception - " + ex.getLocalizedMessage(), PriorityLogger.PriorityLevel.Medium);
        }
        return null;
    }

    public T DeserializeFromFileName(String fileName) {
        return DeserializeFromFilePath(fileDirectory + "\\" + fileName);
    }

    private String readDirectoryFile() {
        File file = new File("userDirectory.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String directory = bufferedReader.readLine();
            if (directory != null) {
                return directory;
            } else {
                throw new IOException("The directory was null.");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Deserializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Deserializer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
