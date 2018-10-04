/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author conno
 */
public class ContentLoader {

    private final FXMLLoader fXMLLoader = new FXMLLoader();

    public TabContentController getTabController(String fxml) {
        try {
            fXMLLoader.setLocation(getClass().getResource(fxml));
            Parent parent = fXMLLoader.load();
            return (TabContentController) fXMLLoader.getController();
        } catch (IOException ex) {
            // log 
            return null;
        }
    }
}
