/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;

/**
 *
 * @author conno
 */
public abstract class ControllerCommand extends Command<Controller> {

    public static class PassToViewCommand extends ControllerCommand {

        private final ModelCommand modelCommand;

        public PassToViewCommand(ModelCommand modelCommand) {
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
}
