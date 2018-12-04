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
import com.albinodevelopment.IO.FileIO;
import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Drink;
import com.albinodevelopment.Model.Components.DrinksList;
import com.albinodevelopment.Model.Components.DrinksTab;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Model;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import com.albinodevelopment.View.TabContent.ContentLoaderFactory;
import com.albinodevelopment.View.TabContent.FunctionTabContent;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jdom2.Document;
import org.jdom2.Element;

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
    private final HashMap<Function, FunctionTabContent> tabs = new HashMap<>();

    @FXML
    private TabPane tabPane;

    public MainWindow() {
        super();
        windowLoaderFactory = new WindowLoaderFactory();
        contentLoaderFactory = new ContentLoaderFactory();
        setupMVC();
    }

    @FXML
    private void handleNewButton(ActionEvent event) {
        newFunction();
    }

    @FXML
    private void handleOpenButton(ActionEvent event) {
        open();
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
        settingsWindow.show();
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
        drinksListBuilderWindow.show();
    }

    @Override
    public void handle(ViewCommand command) {
        if (command.canExecute(this)) {
            ICommand.ExecutionResult exectutionResult = command.execute(this);
            if (exectutionResult.equals(ICommand.ExecutionResult.failure)) {
                ConnorLogger.log("COMMAND FAILURE: " + command.toString()
                        + "\n" + command.getErrorCode(), ConnorLogger.PriorityLevel.High);
            } else {

            }
        } else {
            ConnorLogger.log("Command couldn't be run for some reason " + command.toString(), ConnorLogger.PriorityLevel.High);
        }
    }

    @Override
    public boolean canHandle(Command command) {
        if (command instanceof ViewCommand) {
            // log success
            ConnorLogger.log(command.toString() + "can be handled by this command handler - " + this.getClass().getName(), ConnorLogger.PriorityLevel.Medium);
            return true;
        } else {
            // log failure
            command.generateErrorCode("This command cannot be handled by this Command Handler.");
            ConnorLogger.log(command.getErrorCode(), ConnorLogger.PriorityLevel.High);
            return false;
        }
    }

    @Override
    public void setCommandHandler(ICommandHandler commandHandler) {
        if (this.commandHandler == null && commandHandler != null) {
            this.commandHandler = commandHandler;
        } else {
            // log and output
        }
    }

    private void setupMVC() {
        Controller controller = new Controller();
        Model model = new Model();
        model.setCommandHandler(this);
        this.setCommandHandler(controller);
        controller.setCommandHandler(model);
        controller.start();
    }

    private <T extends Window> Window setupWindow(Class<T> windowClass, String fxml) {
        WindowLoader windowLoader = windowLoaderFactory.getWindowLoader(windowClass);
        try {
            Window window = windowLoader.newWindow(fxml);
            window.setMain(this);
            window.start();
            return window;
        } catch (InstantiationException | IllegalAccessException ex) {
            ConnorLogger.log("ERROR; Couldn't load settings window for some reason - " + ex.toString(), ConnorLogger.PriorityLevel.High);
        }
        return null;
    }

    @Override
    public ICommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    protected void refresh() {
        ConnorLogger.log("Main Window Refreshed.", ConnorLogger.PriorityLevel.Zero);
        ConnorLogger.log(FunctionManager.getInstance().currentFunctions(), ConnorLogger.PriorityLevel.Zero);
    }

    @Override
    public void newFunction() {
        // send a new function command to the model
        handle(new ViewCommand.PassToControllerCommand(new ControllerCommand.PassToModelCommand(new ModelCommand.CallForNewFunctionWindowCommand())));
    }

    @Override
    public void show() {
        stage.show();
    }

    @Override
    public void save() {
        // create a function file and write it to the disk
    }

    @Override
    public void closeTab(String title) {
        ConnorLogger.log("Tab closed: " + title, ConnorLogger.PriorityLevel.Low);
        commandHandler.handle(new ControllerCommand.PassToModelCommand(new ModelCommand.RemoveFunctionCommand(title)));
    }

    @Override
    public void open() {
        // read in a function file and open a tab with it's corresponding details
        Document document = FileIO.getXMLDocumentFromFile(FileIO.openFileExplorer(FileIO.FUNCTION_DIRECTORY()));
        if (document != null) {
            ConnorLogger.log("Found XML Function file.", ConnorLogger.PriorityLevel.Low);
            Element root = document.getRootElement();
            if (root.getName() == "Function") {
                loadFunctionFromFile(root);
            }
        }
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
        functionWindow.show();
    }

    @Override
    public void closeNewFunctionWindow() {
        if (functionWindow != null) {
            functionWindow.close();
        }
    }

    private FunctionTabContent createNewTabContent(Function function) {
        FunctionTabContent tabContent = (FunctionTabContent) contentLoaderFactory.getBuilder().getContentController("FunctionTabContent.fxml");
        tabContent.setMain(this);
        tabs.put(function, tabContent);
        return tabContent;
    }

    private void newTab(Function function) {
        ConnorLogger.log("New Tab!", ConnorLogger.PriorityLevel.Low);
        Tab tab = generateTab(function.getName());
        tab.contentProperty().set(generateTabGUI(function).generateContent(function));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @Override
    public void updateTab(Function function) {
        FunctionTabContent tabContent = tabs.get(function);
        if (tabContent == null) {
            newTab(function);
        } else {
            ConnorLogger.log("Updating Tab!", ConnorLogger.PriorityLevel.Low);
            tabContent.update(function);
        }
    }

    private FunctionTabContent generateTabGUI(Function function) {
        FunctionTabContent tabContent = tabs.get(function);
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

    private void loadFunctionFromFile(Element root) {
        String functionName = root.getAttributeValue("Name");
        Element meta = root.getChild("Metadata");
        String limitString = meta.getChildText("Limit");
        Double limit = Double.valueOf(limitString);
        DrinksTab drinksTab = rebuildDrinksTab(root.getChild("Tab"), limit);
        
        commandHandler.getCommandHandler().handle(new ModelCommand.NewFunctionCommand(functionName, drinksTab));
    }

    private DrinksTab rebuildDrinksTab(Element tabElem, double limit) {
        if (tabElem.getName() == "Tab") {
            IDrinksListInterpreter dli
                    = (IDrinksListInterpreter) ApplicationSettings
                            .getInstance().getSetting(
                                    ISettingsManager.settingsList.DrinksListInterpreter).getValue();
            Element drinksTabElem = tabElem.getChild("DrinksList");
            DrinksList drinksList = dli.interpret(drinksTabElem);
            HashMap<Drink, Integer> count = new HashMap<>();
            for (Element e : drinksTabElem.getChildren("Drink")) {
                Drink drink = drinksList.GetDrink(e.getChildText("Name"));
                int amount = Integer.valueOf(e.getChildText("Count"));
                count.put(drink, amount);
            }

            DrinksTab dt = new DrinksTab(drinksList, limit, count);
            return dt;
        } else {
            ConnorLogger.log("ERROR: Couldn't find tab XML data.", ConnorLogger.PriorityLevel.Medium);
            return null;
        }
    }
}
