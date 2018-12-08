/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.CustomerTab;

/**
 * Abstract class that contains static subclasses that all inherit from. Defines
 * commands that should be run by a "Controller (the C in MVC)" object
 *
 * @author conno
 */
public abstract class ControllerCommand extends Command<Controller> {

    /**
     * Carrier command - takes a ModelCommand from somewhere and passes it to
     * the model to be executed.
     */
    public static class PassToModelCommand extends ControllerCommand {

        /**
         * the Model Command to be passed. This is final so it cannot be edited.
         */
        private final ModelCommand modelCommand;

        /**
         * The only constructor for this class
         *
         * @param modelCommand the model command you want to pass to the model.
         */
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

    /**
     * Static Controller Command that validates the creation of a drink.
     */
    public static class ValidateDrinkCreationCommand extends ControllerCommand {

        /**
         * the name of the drink. FINAL
         */
        private final String name;
        /**
         * the price of the drink (as a String). FINAL
         */
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
            MenuItem menuItem = commandHandler.validateDrinkCreation(name, price);
            if (menuItem != null) {
                commandHandler.getCommandHandler().handle(new ModelCommand.AddDrinkToDrinksListCommand(menuItem));
                return ExecutionResult.success;
            } else {
                commandHandler.handle(
                        new PassToModelCommand(
                                new ModelCommand.PassToViewCommand(
                                        new ViewCommand.PushOutputMessageToMenuBuilderCommand("Please enter a valid price."))));
                return ExecutionResult.failure;
            }
        }
    }

    public static class ValidateDrinkRemovalCommand extends ControllerCommand {

        private final String name;
        private final String price;
        private final MenuItem item;

        public ValidateDrinkRemovalCommand(String name, String price) {
            this.name = name;
            this.price = price;
            this.item = null;
        }

        public ValidateDrinkRemovalCommand(MenuItem item) {
            this.name = null;
            this.price = null;
            this.item = item;
        }

        @Override
        public boolean canExecute(Controller commandHandler) {
            return commandHandler.canHandle(this);
        }

        @Override
        public ExecutionResult execute(Controller commandHandler) {
            if (item == null) {
                MenuItem drink = commandHandler.validateDrinkCreation(name, price);
                if (drink != null) {
                    commandHandler.getCommandHandler().handle(new ModelCommand.RemoveDrinkFromDrinksListCommand(drink));
                    return ExecutionResult.success;
                } else {
                    return ExecutionResult.failure;
                }
            } else {
                commandHandler.getCommandHandler().handle(new ModelCommand.RemoveDrinkFromDrinksListCommand(item));
                return ExecutionResult.success;
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
            CustomerTab drinksTab = commandHandler.validateFunctionCreation(name, limit, drinksListPath);
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
        private final MenuItem drink;
        private final String functionName;

        public ValidateDrinkAmountChangeCommand(int amountChange, MenuItem drink, String functionName) {
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
