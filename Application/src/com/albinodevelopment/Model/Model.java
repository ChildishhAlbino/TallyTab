/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model;

import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand> {

    private ICommandHandler<ViewCommand> commandHandler;

    @Override
    public boolean CanHandle(ModelCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Handle(ModelCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }

}
