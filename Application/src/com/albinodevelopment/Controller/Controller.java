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
import com.albinodevelopment.Model.Components.Drink;

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
        PriorityLogger.LoggingOnOrOff(true);
        PriorityLogger.SetPriority(PriorityLogger.PriorityLevel.Low);
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
            PriorityLogger.Log(command.toString() + ": Success.", PriorityLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.GenerateErrorCode("This command cannot be handled by this Command Handler.");
            PriorityLogger.Log(command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            return false;
        }
    }

    public Drink validateDrinkCreation(String name, String price) {
        try {
            Double d_Price = Double.valueOf(price);
            return new Drink(d_Price, name);
        } catch (NumberFormatException ex) {
            PriorityLogger.Log("ERROR: Price couldn't be converted to double - " + ex.toString(), PriorityLogger.PriorityLevel.Medium);
        }
        return null;
    }
}
