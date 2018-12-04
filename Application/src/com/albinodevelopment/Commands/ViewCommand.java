/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.View.MenuBuilder.MenuBuilderWindow;
import com.albinodevelopment.View.View;

/**
 *
 * @author conno
 */
public abstract class ViewCommand extends Command<View> {

    public static class PassToControllerCommand extends ViewCommand {

        private final Command controllerCommand;

        public PassToControllerCommand(ControllerCommand controllerCommand) {
            this.controllerCommand = controllerCommand;
        }

        @Override
        public boolean canExecute(View commandHandler) {
            return commandHandler.getCommandHandler().canHandle(controllerCommand);
        }

        @Override
        public ExecutionResult execute(View commandHandler) {
            if (canExecute(commandHandler)) {
                commandHandler.getCommandHandler().handle(controllerCommand);
                return ExecutionResult.success;
            } else {
                return ExecutionResult.failure;
            }
        }
    }

    public static class GenerateGUIFromDrinkCommand extends ViewCommand {

        private final MenuItem drink;

        public GenerateGUIFromDrinkCommand(MenuItem drink) {
            this.drink = drink;
        }

        @Override
        public boolean canExecute(View commandHandler) {
            return drink != null;
        }

        @Override
        public ExecutionResult execute(View commandHandler) {
            MenuBuilderWindow drinksListBuilderWindow = (MenuBuilderWindow) commandHandler.getWindowByName("DrinksList");
            drinksListBuilderWindow.createGUIFromDrink(drink);
            return ExecutionResult.success;
        }
    }

    public static class LoadDrinksListCommand extends ViewCommand {

        private final Menu drinkList;

        public LoadDrinksListCommand(Menu drinkList) {
            this.drinkList = drinkList;
        }

        @Override
        public boolean canExecute(View commandHandler) {
            return drinkList != null;
        }

        @Override
        public ExecutionResult execute(View commandHandler) {
            MenuBuilderWindow drinksListBuilderWindow = (MenuBuilderWindow) commandHandler.getWindowByName("DrinksList");
            drinksListBuilderWindow.loadDrinksList(drinkList);
            return ExecutionResult.success;
        }

    }

    public static class OpenNewFunctionWindowCommand extends ViewCommand {

        @Override
        public ExecutionResult execute(View commandHandler) {
            commandHandler.openNewFunctionWindow();
            return ExecutionResult.success;
        }

    }

    public static class CloseNewFunctionWindowCommand extends ViewCommand {

        @Override
        public ExecutionResult execute(View commandHandler) {
            commandHandler.closeNewFunctionWindow();
            return ExecutionResult.success;
        }

    }

    public static class UpdateTabContentCommand extends ViewCommand {

        private final Function function;

        public UpdateTabContentCommand(Function function) {
            this.function = function;
        }

        @Override
        public ExecutionResult execute(View commandHandler) {
            commandHandler.updateTab(function);
            return ExecutionResult.success;
        }
    }
}
