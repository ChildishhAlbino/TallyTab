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
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.DrinksListBuilder;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand> {

    private ICommandHandler<ViewCommand> commandHandler;
    private final DrinksListBuilder drinksListBuilder = DrinksListBuilder.getInstance();
    public final FunctionManager functionManager = FunctionManager.getInstance();

    @Override
    public void setCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }

    @Override
    public ICommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    public void handle(ModelCommand command) {
        if (command.canExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                ConnorLogger.log("COMMAND FAILURE: " + command.toString()
                        + "\n" + command.getErrorCode(), ConnorLogger.PriorityLevel.High);
            } else {

            }
        } else {
            ConnorLogger.log("Command couldn't be run for some reason " + command.toString(), ConnorLogger.PriorityLevel.High);
        }
    }

    @Override
    public boolean canHandle(Command command) {
        if (command instanceof ModelCommand) {
            // log success
            ConnorLogger.log(command.toString() + "can be handled by this command handler - " + this.getClass().getName(), ConnorLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.generateErrorCode("This command cannot be handled by this Command Handler.");
            ConnorLogger.log(command.getErrorCode(), ConnorLogger.PriorityLevel.High);
            return false;
        }
    }

    public void createNewDrinksList() {
        if (drinksListIsNull()) {
            drinksListBuilder.create();
        } else {
            ConnorLogger.log("ERROR: Drinks list already being built. Cannot create new.", ConnorLogger.PriorityLevel.High);
        }
    }

    public void addDrinkToDrinksList(Drink drink) {
        createNewDrinksList();
        drinksListBuilder.get().add(drink);
    }

    public void removeDrinkFromDrinksList(Drink drink) {
        createNewDrinksList();
        drinksListBuilder.get().remove(drink);
    }

    public void openDrinksList() {
        drinksListBuilder.open();
    }

    public void saveDrinksList() {
        drinksListBuilder.save();
    }

    public DrinksListBuilder getDrinksListBuilder() {
        return drinksListBuilder;
    }

    public boolean drinksListIsNull() {
        return drinksListBuilder.get() == null;
    }

}
