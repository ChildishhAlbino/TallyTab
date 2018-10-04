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
 * @param <T> 
 */
public interface IContent<T> {
    Parent generateContent(T input);
    void setFromFXML(Parent fromFXML);
}
