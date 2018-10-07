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
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Builders.DrinksListBuilder;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.View.TabContent.TabContent;
import java.util.HashMap;

/**
 *
 * @author conno
 */
public class Controller extends Thread implements ICommandHandler<ControllerCommand> {

    //private boolean running = true;
    private ICommandHandler<ModelCommand> commandHandler;
    //private final HashMap<TabContent, String> tabsToFunctionNames = new HashMap<>();

    @Override
    public boolean canHandle(Command command) {
        if (command instanceof ControllerCommand) {
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

    @Override
    public void handle(ControllerCommand command) {
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
    public void run() {
        ConnorLogger.setLoggingState(true);
        ConnorLogger.setPriority(ConnorLogger.PriorityLevel.Low);
        ConnorLogger.log("Logging is on", ConnorLogger.PriorityLevel.High);
    }

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

    public Drink validateDrinkCreation(String name, String price) {
        Double d_Price = validateDouble(price);
        if (d_Price == null) {
            return null;
        }
        return new Drink(d_Price, name);
    }

    public DrinksTab validateFunctionCreation(String name, String limit, String drinksListPath) {
        Double d_Limit = validateLimit(limit);
        if (DrinksListBuilder.getInstance().validate(drinksListPath) && d_Limit != null) {
            DrinksList drinksList = DrinksListBuilder.getInstance().openAndGet(drinksListPath);
            DrinksTab drinksTab = new DrinksTab(drinksList, d_Limit);
            return drinksTab;
        }

        return null;
    }

    private Double validateDouble(String toBeValidated) {
        try {
            Double d = Double.valueOf(toBeValidated);
            return d;
        } catch (NumberFormatException ex) {
            ConnorLogger.log("ERROR: Price couldn't be converted to double - " + ex.toString(), ConnorLogger.PriorityLevel.Medium);
        }
        return null;
    }

    private Double validateLimit(String limit) {
        Double d_Limit;
        if ("".equals(limit)) {
            d_Limit = Double.POSITIVE_INFINITY;
        } else {
            d_Limit = validateDouble(limit);
        }
        return d_Limit;
    }

    public boolean validateDrinkAmountChange(String functionName) {
        return true;
    }

}
