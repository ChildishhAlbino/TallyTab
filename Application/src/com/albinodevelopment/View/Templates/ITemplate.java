/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View.Templates;

import javafx.scene.Parent;

/**
 *
 * @author conno
 * @param <T>
 */
public interface ITemplate<T> {

    Parent generate(T input);

    void setFromFXML(Parent fromFXML);

    public String getText();

    void update(T input);
}
