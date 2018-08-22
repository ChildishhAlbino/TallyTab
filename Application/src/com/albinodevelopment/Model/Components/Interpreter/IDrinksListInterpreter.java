/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Interpreter;

import com.albinodevelopment.Model.Components.DrinksList;

/**
 *
 * @author conno
 */
public interface IDrinksListInterpreter {

    DrinksList Interpret(String directory);
}
