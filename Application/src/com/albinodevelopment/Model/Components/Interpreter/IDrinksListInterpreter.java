/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.Model.Components.Menu;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public interface IDrinksListInterpreter {

    Menu interpret(String directory);
    Menu interpret(Element root);
    void save(Menu drinksList);
}
