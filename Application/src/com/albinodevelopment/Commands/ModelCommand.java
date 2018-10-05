/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksTab;
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

        public PassToViewCommand(ControllerCommand viewCommand) {
            this.viewCommand = viewCommand;
        }

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.GetCommandHandler().CanHandle(viewCommand);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            if (CanExecute(commandHandler)) {
                commandHandler.GetCommandHandler().Handle(viewCommand);
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class NewFunctionCommand extends ModelCommand {

        private final String name;
        private final DrinksTab drinksTab;

        public NewFunctionCommand(String name, DrinksTab drinksTab) {
            this.name = name;
            this.drinksTab = drinksTab;
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.functionManager.newFunction(name, drinksTab);
            commandHandler.GetCommandHandler().Handle(new ViewCommand.CloseNewFunctionWindowCommand());
            commandHandler.GetCommandHandler().Handle(new ViewCommand.UpdateTabContentCommand(FunctionManager.getInstance().getFunction(name)));
            return ExecutionResult.success;
        }
    }

    public static class CreateDrinksListCommand extends ModelCommand {

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.drinksListIsNull();
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.createNewDrinksList();
            return ExecutionResult.success;
        }

    }

    public static class OpenDrinksListCommand extends ModelCommand {

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.openDrinksList();
            commandHandler.GetCommandHandler().Handle(new ViewCommand.LoadDrinksListCommand(commandHandler.getDrinksListBuilder().get()));
            return ExecutionResult.success;
        }

    }

    public static class SaveDrinksListCommand extends ModelCommand {

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.saveDrinksList();
            return ExecutionResult.success;
        }

    }

    public static class AddDrinkToDrinksListCommand extends ModelCommand {

        private final Drink drink;

        public AddDrinkToDrinksListCommand(Drink drink) {
            this.drink = drink;
        }

        @Override
        public boolean CanExecute(Model commandHandler) {
            return (drink != null);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.addDrinkToDrinksList(drink);
            commandHandler.GetCommandHandler().Handle(new ViewCommand.GenerateGUIFromDrinkCommand(drink));
            return ExecutionResult.success;
        }

    }

    public static class RemoveDrinkFromDrinksListCommand extends ModelCommand {

        private final Drink drink;

        public RemoveDrinkFromDrinksListCommand(Drink drink) {
            this.drink = drink;
        }

        @Override
        public boolean CanExecute(Model commandHandler) {
            return (drink != null);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.removeDrinkFromDrinksList(drink);
            return ExecutionResult.success;
        }

    }

    public static class CallForNewFunctionWindowCommand extends ModelCommand {

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.GetCommandHandler().Handle(new ViewCommand.OpenNewFunctionWindowCommand());
            return ExecutionResult.success;
        }

    }

    public static class ChangeDrinkAmountCommand extends ModelCommand {

        private final int delta;
        private final String functionName;
        private final Drink drink;

        public ChangeDrinkAmountCommand(int delta, String functionName, Drink drink) {
            this.delta = delta;
            this.functionName = functionName;
            this.drink = drink;
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            Function function = commandHandler.functionManager.changeDrinkValue(functionName, drink, delta);
            if (function != null) {
                commandHandler.GetCommandHandler().Handle(new ViewCommand.UpdateTabContentCommand(function));
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
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.functionManager.remove(functionName);
            return ExecutionResult.success;
        }

    }
}
