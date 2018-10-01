/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Model;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;

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
        private final double limit;
        private final String directory;

        public NewFunctionCommand(String name, double limit, String directory) {
            this.name = name;
            this.limit = limit;
            this.directory = directory;
        }

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            IDrinksListInterpreter drinksListInterpreter
                    = (IDrinksListInterpreter) ApplicationSettings.GetInstance().
                            getSetting(ISettingsManager.settingsList.DrinksListInterpreter).
                            getValue();
            commandHandler.NewFunction(name, limit,
                    drinksListInterpreter.Interpret(directory));
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

}
