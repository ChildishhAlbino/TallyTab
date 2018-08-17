/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model;

import com.albinodevelopment.Commands.Command;
import com.albinodevelopment.Commands.ICommand;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Logging.PriorityLogger;
import com.albinodevelopment.Model.Components.DrinksList;
import java.util.ArrayList;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand> {

    private ICommandHandler<ViewCommand> commandHandler;
    
    @Override
    public void Handle(ModelCommand command) {
        if (command.CanExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                // log
            }
        } else {
            // log
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
        if (command instanceof ModelCommand) {
            // log success
            PriorityLogger.Log(command.toString() + ": Success.", PriorityLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.GenerateErrorCode("This command cannot be handled by this Command Handler.");
            PriorityLogger.Log(command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            return false;
        }
    }

}
