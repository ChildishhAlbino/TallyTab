/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Controller;

import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ModelCommand;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author conno
 */
public class Controller extends Thread implements ICommandHandler<ControllerCommand> {
    private boolean running = true;
    private ICommandHandler<ModelCommand> commandHandler;

    @Override
    public boolean CanHandle(ControllerCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Handle(ControllerCommand command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Controller");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
