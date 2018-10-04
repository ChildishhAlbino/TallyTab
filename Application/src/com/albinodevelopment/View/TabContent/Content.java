/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import javafx.scene.Parent;

/**
 *
 * @author conno
 */
public abstract class Content {

    protected Parent fromFXML;

    public void setFromFXML(Parent parent) {
        this.fromFXML = parent;
    }
}
