/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Functions;

import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.CustomerTab;
import java.util.HashMap;
import org.jdom2.Document;

/**
 * Class that stores, edits, and outputs Functions to users. Class is
 * implemented as a singleton to avoid multiple copies of functions and to
 * improve fluidity
 *
 * @author conno
 */
public class FunctionManager {

    //START// singleton implementation
    private static FunctionManager instance;

    public static FunctionManager getInstance() {
        if (instance == null) {
            instance = new FunctionManager();
        }
        return instance;
    }
    //END// singleton implementation

    //START// instance methods and variables
    private final HashMap<String, Function> functions = new HashMap<>();

    public Function changeDrinkValue(String functionName, MenuItem drink, int amount) {
        functions.get(functionName).getDrinksTab().changeItemAmount(amount, drink);
        Function function = functions.get(functionName);
        return function;
    }

    public boolean newFunction(String name, CustomerTab drinksTab) {
        if (!functionAlreadyExists(name)) {
            Function function = new Function(name, drinksTab);
            functions.put(name, function);
            return true;
        }
        return false;
    }

    private boolean functionAlreadyExists(String name) {
        for (Function function : functions.values()) {
            if (function.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Function getFunction(String functionName) {
        Function function = functions.get(functionName);
        return function;
    }

    public void remove(String functionName) {
        functions.remove(functionName);
        ConnorLogger.log("Remove Function: " + functionName + " from the list.", ConnorLogger.PriorityLevel.Low);
    }

    public String currentFunctions() {
        String s = "Current Functions: " + functions.size() + "\n";
        for (Function f : functions.values()) {
            s += f.getName() + ",\n";
        }

        return s;
    }
    
    public boolean saveFunction(String name){
         Document toXML = new Document();
         toXML.addContent(functions.get(name).toXML());
         FileIO.writeXMLDocumentToFile(toXML, FileIO.FUNCTION_DIRECTORY(), name + ".xml");
         return true;
    }
    
    //END// instance methods and variables
}
