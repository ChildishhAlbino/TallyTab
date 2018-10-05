/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.TabContent;

import com.albinodevelopment.View.View;
import javafx.scene.Parent;

/**
 *
 * @author conno
 */
public abstract class Content {
    protected View view;
    protected Parent fromFXML;

    public void setFromFXML(Parent parent) {
        this.fromFXML = parent;
    }
    
    public void setMain(View view){
        this.view = view;
    }
}
