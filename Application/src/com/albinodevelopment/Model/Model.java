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
import com.albinodevelopment.Model.Components.Builders.DrinksListBuilder;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.Function;
import java.util.ArrayList;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand> {

    private ICommandHandler<ViewCommand> commandHandler;
    private final ArrayList<Function> functions = new ArrayList<>();
    private final DrinksListBuilder drinksListBuilder = DrinksListBuilder.getInstance();
    
    @Override
    public void Handle(ModelCommand command) {
        if (command.CanExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                PriorityLogger.Log("COMMAND FAILURE: " + command.GetErrorCode(), PriorityLogger.PriorityLevel.High);
            }
        } else {
            PriorityLogger.Log("Command couldn't be run for some reason " + command.toString(), PriorityLogger.PriorityLevel.High);
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

    public void NewFunction(String name, double Limit, DrinksList drinksList) {
        Function newFunction = new Function(name, new DrinksTab(drinksList, Limit));
        functions.add(newFunction);
    }

    public void createNewDrinksList() {
        if (drinksListIsNull()) {
            drinksListBuilder.create();
        } else {
            PriorityLogger.Log("ERROR: Drinks list already being built. Cannot create new.", PriorityLogger.PriorityLevel.High);
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

    public DrinksListBuilder getDrinksListBuilder() {
        return drinksListBuilder;
    }

    public boolean drinksListIsNull() {
        return drinksListBuilder.get() == null;
    }

}
