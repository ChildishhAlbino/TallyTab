/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.Function;

/**
 *
 * @author conno
 */
public abstract class ControllerCommand extends Command<Controller> {

    public static class PassToModelCommand extends ControllerCommand {

        private final ModelCommand modelCommand;

        public PassToModelCommand(ModelCommand modelCommand) {
            this.modelCommand = modelCommand;
        }

        @Override
        public boolean CanExecute(Controller commandHandler) {
            return commandHandler.GetCommandHandler().CanHandle(modelCommand);
        }

        @Override
        public ExecutionResult Execute(Controller commandHandler) {
            if (CanExecute(commandHandler)) {
                commandHandler.GetCommandHandler().Handle(modelCommand);
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class ValidateDrinkCreationCommand extends ControllerCommand {

        private final String name;
        private final String price;

        public ValidateDrinkCreationCommand(String name, String price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public boolean CanExecute(Controller commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Controller commandHandler) {
            Drink drink = commandHandler.validateDrinkCreation(name, price);
            if (drink != null) {
                commandHandler.GetCommandHandler().Handle(new ModelCommand.AddDrinkToDrinksListCommand(drink));
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class ValidateDrinkRemovalCommand extends ControllerCommand {

        private final String name;
        private final String price;

        public ValidateDrinkRemovalCommand(String name, String price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public boolean CanExecute(Controller commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Controller commandHandler) {
            Drink drink = commandHandler.validateDrinkCreation(name, price);
            if (drink != null) {
                commandHandler.GetCommandHandler().Handle(new ModelCommand.RemoveDrinkFromDrinksListCommand(drink));
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class ValidateFunctionCommand extends ControllerCommand {

        private final String name;
        private final String limit;
        private final String drinksListPath;

        public ValidateFunctionCommand(String name, String limit, String drinksListPath) {
            this.name = name;
            this.limit = limit;
            this.drinksListPath = drinksListPath;
        }

        @Override
        public ExecutionResult Execute(Controller commandHandler) {
            Function function = commandHandler.validateFunctionCreation(name, limit, drinksListPath);
            if (function == null) {
                GenerateErrorCode("Function couldn't be validated. Plese check logs for further explanation.");
                return ExecutionResult.failure;
            }
            commandHandler.GetCommandHandler().Handle(new ModelCommand.NewFunctionCommand(function));
            return ExecutionResult.success;
        }
    }
}
