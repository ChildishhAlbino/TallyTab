/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Model.Model;
import com.albinodevelopment.Settings.ApplicationSettings;

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

        public NewFunctionCommand(String name, double limit) {
            this.name = name;
            this.limit = limit;
        }

        @Override
        public boolean CanExecute(Model commandHandler) {
            return commandHandler.CanHandle(this);
        }

        @Override
        public ExecutionResult Execute(Model commandHandler) {
            commandHandler.NewFunction(name, limit,
                    ApplicationSettings.GetInstance().GetDrinksListInterpreter()
                            .Interpret());
            return ExecutionResult.success;
        }
    }

}
