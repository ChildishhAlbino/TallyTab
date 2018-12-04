/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.View.MainWindow.Windows;

/**
 *
 * @author conno
 */
public abstract class View extends Window implements IView, ICommandHandler<ViewCommand> {

    public View() {
        super();
    }

    public Window getWindowByName(Windows name) {
        return null;
    }

    public void openNewFunctionWindow() {
        // overriden by subclasses
        // should probably be in IView
    }
}
