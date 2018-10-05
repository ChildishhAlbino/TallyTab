/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

import com.albinodevelopment.Commands.Command;
import com.albinodevelopment.Commands.ControllerCommand;
import com.albinodevelopment.Commands.ICommand;
import com.albinodevelopment.Commands.ICommandHandler;
import com.albinodevelopment.Commands.ModelCommand;
import com.albinodevelopment.Commands.ViewCommand;
import com.albinodevelopment.Controller.Controller;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import com.albinodevelopment.Model.Model;
import com.albinodevelopment.View.TabContent.ContentLoaderFactory;
import com.albinodevelopment.View.TabContent.TabContent;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author conno
 */
public class MainWindow extends View implements Initializable {

    private ICommandHandler<ControllerCommand> commandHandler;
    private final WindowLoaderFactory windowLoaderFactory;
    private final ContentLoaderFactory contentLoaderFactory;
    private Window settingsWindow;
    private Window drinksListBuilderWindow;
    private Window functionWindow;
    private final HashMap<Function, TabContent> tabs = new HashMap<>();

    @FXML
    private TabPane tabPane;

    public MainWindow() {
        super();
        windowLoaderFactory = new WindowLoaderFactory();
        contentLoaderFactory = new ContentLoaderFactory();
        SetupMVC();
    }

    @FXML
    private void handleNewButton(ActionEvent event) {
        newFunction();
    }

    @FXML
    private void handleOpenButton(ActionEvent event) {
        Show();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        save();
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        //Close();
    }

    @FXML
    private void handlePreferencesButton(ActionEvent event) {
        if (settingsWindow == null) {
            Window window = setupWindow(com.albinodevelopment.View.SettingsWindow.class, "SettingsWindowFXML.fxml");
            if (window != null) {
                settingsWindow = window;
            } else {
                return;
            }
        }
        settingsWindow.Show();
    }

    @FXML
    private void handleDrinksListButton(ActionEvent event) {
        if (drinksListBuilderWindow == null) {
            Window window = setupWindow(com.albinodevelopment.View.DrinksListBuilder.DrinksListBuilderWindow.class, "DrinksListBuilder/DrinksListBuilderWindowFXML.fxml");
            if (window != null) {
                drinksListBuilderWindow = window;
            } else {
                return;
            }
        }
        drinksListBuilderWindow.Show();
    }

    @Override
    public void Handle(ViewCommand command) {
        if (command.CanExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.Execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                ConnorLogger.Log("COMMAND FAILURE: " + command.toString()
                        + "\n" + command.GetErrorCode(), ConnorLogger.PriorityLevel.High);
            } else {

            }
        } else {
            ConnorLogger.Log("Command couldn't be run for some reason " + command.toString(), ConnorLogger.PriorityLevel.High);
        }
    }

    @Override
    public boolean CanHandle(Command command) {
        if (command instanceof ViewCommand) {
            // log success
            ConnorLogger.Log(command.toString() + "can be handled by this command handler - " + this.getClass().getName(), ConnorLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.GenerateErrorCode("This command cannot be handled by this Command Handler.");
            ConnorLogger.Log(command.GetErrorCode(), ConnorLogger.PriorityLevel.High);
            return false;
        }
    }

    @Override
    public void SetCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }

    private void SetupMVC() {
        Controller controller = new Controller();
        Model model = new Model();
        model.SetCommandHandler(this);
        this.SetCommandHandler(controller);
        controller.SetCommandHandler(model);
        controller.start();
    }

    private <T extends Window> Window setupWindow(Class<T> windowClass, String fxml) {
        WindowLoader windowLoader = windowLoaderFactory.getWindowLoader(windowClass);
        try {
            Window window = windowLoader.NewWindow(fxml);
            window.setMain(this);
            window.start();
            return window;
        } catch (InstantiationException | IllegalAccessException ex) {
            ConnorLogger.Log("ERROR; Couldn't load settings window for some reason - " + ex.toString(), ConnorLogger.PriorityLevel.High);
        }
        return null;
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return commandHandler;
    }

    @Override
    protected void Refresh() {
        ConnorLogger.Log("Main Window Refreshed.", ConnorLogger.PriorityLevel.Zero);
        ConnorLogger.Log(FunctionManager.getInstance().currentFunctions(), ConnorLogger.PriorityLevel.Zero);
    }

    @Override
    public void newFunction() {
        // send a new function command to the model
        Handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.CallForNewFunctionWindowCommand())));
    }

    @Override
    public void Show() {
        stage.show();
    }

    @Override
    public void save() {
        // create a function file and write it to the disk
    }

    @Override
    public void closeTab(String title) {
        ConnorLogger.Log("Tab closed: " + title, ConnorLogger.PriorityLevel.Low);
        commandHandler.Handle(new ControllerCommand.PassToModelCommand(new ModelCommand.RemoveFunctionCommand(title)));
    }

    @Override
    public void open() {
        // read in a function file and open a tab with it's corresponding details
    }

    @Override
    public Window getWindowByName(String name) {
        Window window = null;
        switch (name) {
            case "DrinksList":
                window = drinksListBuilderWindow;
                break;
            case "Settings":
                window = settingsWindow;
                break;
        }
        return window;
    }

    @Override
    public void openNewFunctionWindow() {
        // creates a new newFunctionWindow each time (prevents data from being mutated between multiple new functions)
        // opens it 
        Window window = setupWindow(com.albinodevelopment.View.NewFunctionWindow.class, "NewFunctionWindowFXML.fxml");
        if (window != null) {
            functionWindow = window;
        } else {
            return;
        }
        functionWindow.Show();
    }

    @Override
    public void closeNewFunctionWindow() {
        if (functionWindow != null) {
            functionWindow.Close();
        }
    }

    private TabContent createNewTabContent(Function function) {
        TabContent tabContent = (TabContent) contentLoaderFactory.getBuilder().getContentController("TabContent.fxml");
        tabContent.setMain(this);
        tabs.put(function, tabContent);
        return tabContent;
    }

    private void newTab(Function function) {
        ConnorLogger.Log("New Tab!", ConnorLogger.PriorityLevel.Low);
        Tab tab = generateTab(function.GetName());
        tab.contentProperty().set(generateTabGUI(function).generateContent(function));
        tabPane.getTabs().add(tab);
    }

    @Override
    public void updateTab(Function function) {
        TabContent tabContent = tabs.get(function);
        if (tabContent == null) {
            newTab(function);
        } else {
            ConnorLogger.Log("Updating Tab!", ConnorLogger.PriorityLevel.Low);
            tabContent.update(function);
        }
    }

    private TabContent generateTabGUI(Function function) {
        TabContent tabContent = tabs.get(function);
        if (tabContent == null) {
            tabContent = createNewTabContent(function);
        }
        return tabContent;
    }

    private Tab generateTab(String name) {
        Tab tab = new Tab(name);
        tab.setOnClosed((Event event) -> {
            Tab source = (Tab) event.getSource();
            closeTab(source.getText());
        });
        return tab;
    }
}
