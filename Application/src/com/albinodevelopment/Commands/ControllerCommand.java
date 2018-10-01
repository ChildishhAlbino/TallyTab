/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Components.Drink;

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
}
