/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.CustomerTab;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import com.albinodevelopment.Model.Model;

/**
 *
 * @author conno
 */
public abstract class ModelCommand extends Command<Model> {

    public static class PassToViewCommand extends ModelCommand {

        private final Command viewCommand;

        public PassToViewCommand(ViewCommand viewCommand) {
            this.viewCommand = viewCommand;
        }

        @Override
        public boolean canExecute(Model commandHandler) {
            return commandHandler.getCommandHandler().canHandle(viewCommand);
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            if (canExecute(commandHandler)) {
                commandHandler.getCommandHandler().handle(viewCommand);
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class NewFunctionCommand extends ModelCommand {

        private final String name;
        private final CustomerTab drinksTab;

        public NewFunctionCommand(String name, CustomerTab drinksTab) {
            this.name = name;
            this.drinksTab = drinksTab;
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.functionManager.newFunction(name, drinksTab); // function manager is singleton - why do this?
            commandHandler.getCommandHandler().handle(new ViewCommand.CloseNewFunctionWindowCommand());
            commandHandler.getCommandHandler().handle(new ViewCommand.UpdateTabContentCommand(FunctionManager.getInstance().getFunction(name)));
            return ExecutionResult.success;
        }
    }

    public static class CreateDrinksListCommand extends ModelCommand {

        @Override
        public boolean canExecute(Model commandHandler) {
            return commandHandler.drinksListIsNull();
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.createNewDrinksList();
            return ExecutionResult.success;
        }

    }

    public static class OpenDrinksListCommand extends ModelCommand {

        @Override
        public boolean canExecute(Model commandHandler) {
            return commandHandler.canHandle(this);
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.openDrinksList();
            commandHandler.getCommandHandler().handle(new ViewCommand.LoadMenuCommand("Loaded menu from file."));
            return ExecutionResult.success;
        }

    }

    public static class SaveDrinksListCommand extends ModelCommand {

        @Override
        public boolean canExecute(Model commandHandler) {
            return commandHandler.canHandle(this);
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.saveDrinksList();
            return ExecutionResult.success;
        }

    }

    public static class AddDrinkToDrinksListCommand extends ModelCommand {

        private final MenuItem drink;

        public AddDrinkToDrinksListCommand(MenuItem drink) {
            this.drink = drink;
        }

        @Override
        public boolean canExecute(Model commandHandler) {
            return (drink != null);
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.addDrinkToDrinksList(drink);
            commandHandler.getCommandHandler().handle(new ViewCommand.LoadMenuCommand("Added item: " + drink.getName()));
            return ExecutionResult.success;
        }

    }

    public static class RemoveDrinkFromDrinksListCommand extends ModelCommand {

        private final MenuItem drink;

        public RemoveDrinkFromDrinksListCommand(MenuItem drink) {
            this.drink = drink;
        }

        @Override
        public boolean canExecute(Model commandHandler) {
            return (drink != null);
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.removeDrinkFromDrinksList(drink);
            return ExecutionResult.success;
        }

    }

    public static class CallForNewFunctionWindowCommand extends ModelCommand {

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.getCommandHandler().handle(new ViewCommand.OpenNewFunctionWindowCommand());
            return ExecutionResult.success;
        }

    }

    public static class ChangeDrinkAmountCommand extends ModelCommand {

        private final int delta;
        private final String functionName;
        private final MenuItem drink;

        public ChangeDrinkAmountCommand(int delta, String functionName, MenuItem drink) {
            this.delta = delta;
            this.functionName = functionName;
            this.drink = drink;
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            Function function = commandHandler.functionManager.changeDrinkValue(functionName, drink, delta);
            if (function != null) {
                commandHandler.getCommandHandler().handle(new ViewCommand.UpdateTabContentCommand(function));
                return ExecutionResult.success;
            }
            return ExecutionResult.failure;
        }
    }

    public static class RemoveFunctionCommand extends ModelCommand {

        private final String functionName;

        public RemoveFunctionCommand(String functionName) {
            this.functionName = functionName;
        }

        @Override
        public ExecutionResult execute(Model commandHandler) {
            commandHandler.functionManager.remove(functionName);
            return ExecutionResult.success;
        }
    }

    public static class EditLimitCommand extends ModelCommand {

        public final Function function;
        public final double limit;

        public EditLimitCommand(Function function, double limit) {
            this.function = function;
            this.limit = limit;
        }

        @Override

        public ExecutionResult execute(Model commandHandler) {
            if (commandHandler.functionManager.getFunction(function.getName()).getDrinksTab().ChangeLimit(limit)) {
                commandHandler.getCommandHandler().handle(new ViewCommand.UpdateTabContentCommand(function));
                commandHandler.handle(new PassToViewCommand(new ViewCommand.PushOutputMessageToFunctionTabCommand(function, "Successfully edited limit.")));
                return ExecutionResult.success;
            } else {
                commandHandler.handle(new PassToViewCommand(new ViewCommand.PushOutputMessageToFunctionTabCommand(function, "New limit was smaller than current balance.")));
                return ExecutionResult.failure;
            }
        }

    }
}
