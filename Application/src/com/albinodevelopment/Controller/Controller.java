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
    public boolean CanHandle(Command command) {
        if (command instanceof ControllerCommand) {
            // log success
            ConnorLogger.Log(command.toString() + "can be handled by this command handler - " + this.getClass().getName(), ConnorLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.GenerateErrorCode("This command cannot be handled by this Command Handler.");
            ConnorLogger.Log(command.GetErrorCode(), ConnorLogger.PriorityLevel.High);
            return false;
        }
    }

    @Override
    public void Handle(ControllerCommand command) {
        if (command.CanExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                ConnorLogger.Log("COMMAND FAILURE: " + command.toString()
                        + "\n" + command.GetErrorCode(), ConnorLogger.PriorityLevel.High);
            } else {

            }
        } else {
            ConnorLogger.Log("Command couldn't be run for some reason " + command.toString(), ConnorLogger.PriorityLevel.High);
        }
    }

    @Override
    public void run() {
        ConnorLogger.LoggingOnOrOff(true);
        ConnorLogger.SetPriority(ConnorLogger.PriorityLevel.Low);
        ConnorLogger.Log("Logging is on", ConnorLogger.PriorityLevel.High);
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
            ConnorLogger.Log("ERROR: Price couldn't be converted to double - " + ex.toString(), ConnorLogger.PriorityLevel.Medium);
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

//    public void addToMap(TabContent tabContent, Function function) {
//        tabsToFunctionNames.put(tabContent, function.GetName());
//    }
//
//    public void removeFromMap(TabContent tabContent) {
//        tabsToFunctionNames.remove(tabContent);
//    }
//
//    public void removeFromMap(String functionName) {
//        tabsToFunctionNames.values().remove(functionName);
//        ConnorLogger.Log(tabsToFunctionNames.toString(), ConnorLogger.PriorityLevel.Medium);
//    }
//
//    public String getFromMap(TabContent tabContent) {
//        return tabsToFunctionNames.get(tabContent);
//    }

    public boolean validateDrinkAmountChange(String functionName) {
        return true;
    }

}
