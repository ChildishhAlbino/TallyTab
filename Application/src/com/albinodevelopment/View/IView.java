/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.View.Templates.FunctionTemplate;
import com.albinodevelopment.View.Templates.MenuBuilderTemplate;

/**
 *
 * @author conno
 */
public interface IView {

    void newFunction();

    void open();

    void save();

    void closeTab(String title);

    //void newTab(Function function);
    void updateTab(Function function);

    void closeNewFunctionWindow();
    
    void updateMenuBuilderWindow(Menu menu);
    
    MenuBuilderTemplate getMenuBuilderWindow();
    
    FunctionTemplate getFunctionTemplate(Function function);
}
