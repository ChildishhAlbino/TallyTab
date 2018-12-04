/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.Model.Components.DrinksList;
import java.io.Serializable;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class DatabaseDrinksListInterpreter implements IDrinksListInterpreter, Serializable {

    @Override
    public DrinksList interpret(String directory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(DrinksList drinksList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DrinksList interpret(Element root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
