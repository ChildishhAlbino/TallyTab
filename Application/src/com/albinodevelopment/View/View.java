/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ViewCommand;

/**
 *
 * @author conno
 */
public abstract class View extends Thread implements IView, ICommandHandler<ViewCommand> {
    
}
