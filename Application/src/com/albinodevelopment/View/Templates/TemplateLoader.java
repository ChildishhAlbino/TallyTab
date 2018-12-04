/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author conno
 * @param <T> the type this content loader returns
 */
public class TemplateLoader<T extends ITemplate<?>> {

    private final FXMLLoader fXMLLoader = new FXMLLoader();

    public T getContentController(String fxml) {
        try {
            fXMLLoader.setLocation(getClass().getResource(fxml));
            Parent parent = fXMLLoader.load();
            T t = (T) fXMLLoader.getController();
            t.setFromFXML(parent);
            return t;
        } catch (IOException ex) {
            // log 
            return null;
        }
    }
}
