/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Controller;

import com.albinodevelopment.Commands.Command;
import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ICommand;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Logging.PriorityLogger;

/**
 *
 * @author conno
 */
public class Controller extends Thread implements ICommandHandler<ControllerCommand> {

    private boolean running = true;
    private ICommandHandler<ModelCommand> commandHandler;

    @Override
    public void Handle(ControllerCommand command) {
        if (command.CanExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                // log
            }
        }
    }

    @Override
    public void run() {
        PriorityLogger.LoggingOnOrOff(false);
        PriorityLogger.SetPriority(PriorityLogger.PriorityLevel.High);
        PriorityLogger.Log("Logging is on", PriorityLogger.PriorityLevel.High);

        while (running) {
            System.out.println("Controller");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException ex) {

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

    @Override
    public ICommandHandler GetCommandHandler() {
        return commandHandler;
    }

    @Override
    public boolean CanHandle(Command command) {
        if (command instanceof ControllerCommand) {
            // log success

            return true;
        } else {
            // log failure
            PriorityLogger.Log(command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            return false;
        }

    }
}
