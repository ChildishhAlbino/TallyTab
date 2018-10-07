/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.View.TabContent.TabContent;

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
        public boolean canExecute(Controller commandHandler) {
            return commandHandler.getCommandHandler().canHandle(modelCommand);
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            if (canExecute(commandHandler)) {
                commandHandler.getCommandHandler().handle(modelCommand);
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
        public boolean canExecute(Controller commandHandler) {
            return commandHandler.canHandle(this);
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            Drink drink = commandHandler.validateDrinkCreation(name, price);
            if (drink != null) {
                commandHandler.getCommandHandler().handle(new ModelCommand.AddDrinkToDrinksListCommand(drink));
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
        public boolean canExecute(Controller commandHandler) {
            return commandHandler.canHandle(this);
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            Drink drink = commandHandler.validateDrinkCreation(name, price);
            if (drink != null) {
                commandHandler.getCommandHandler().handle(new ModelCommand.RemoveDrinkFromDrinksListCommand(drink));
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class ValidateNewFunctionCommand extends ControllerCommand {

        private final String name;
        private final String limit;
        private final String drinksListPath;

        public ValidateNewFunctionCommand(String name, String limit, String drinksListPath) {
            this.name = name;
            this.limit = limit;
            this.drinksListPath = drinksListPath;
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            DrinksTab drinksTab = commandHandler.validateFunctionCreation(name, limit, drinksListPath);
            if (drinksTab == null) {
                generateErrorCode("Function couldn't be validated. Plese check logs for further explanation.");
                return ExecutionResult.failure;
            }
            commandHandler.getCommandHandler().handle(new ModelCommand.NewFunctionCommand(name, drinksTab));
            return ExecutionResult.success;
        }
    }

//    public static class CreateMapEntryCommand extends ControllerCommand {
//
//        private final Function function;
//        private final TabContent tabContent;
//
//        public CreateMapEntryCommand(Function function, TabContent tabContent) {
//            this.function = function;
//            this.tabContent = tabContent;
//        }
//
//        @Override
//        public ExecutionResult Execute(Controller commandHandler) {
//            commandHandler.addToMap(tabContent, function);
//            return ExecutionResult.success;
//        }
//
//    }
    public static class ValidateDrinkAmountChangeCommand extends ControllerCommand {

        private final int amountChange;
        private final Drink drink;
        private final String functionName;

        public ValidateDrinkAmountChangeCommand(int amountChange, Drink drink, String functionName) {
            this.amountChange = amountChange;
            this.drink = drink;
            this.functionName = functionName;
        }

        @Override
        public boolean canExecute(Controller commandHandler) {
            return drink != null && functionName != null;
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            if (commandHandler.validateDrinkAmountChange(functionName)) {
                commandHandler.getCommandHandler().handle(new ModelCommand.ChangeDrinkAmountCommand(amountChange, functionName, drink));
                return ExecutionResult.success;
            } else {
                generateErrorCode("Drink amount change was not valid.");
            }
            return ExecutionResult.failure;
        }

    }

//    public static class RemoveTabContentValueCommand extends ControllerCommand {
//
//        private final String functionName;
//
//        public RemoveTabContentValueCommand(String functionName) {
//            this.functionName = functionName;
//        }
//
//        @Override
//        public ExecutionResult Execute(Controller commandHandler) {
//            commandHandler.removeFromMap(functionName);
//            return ExecutionResult.success;
//        }
//
//    }
}
