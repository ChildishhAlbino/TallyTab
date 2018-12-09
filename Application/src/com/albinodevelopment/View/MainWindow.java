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
import com.albinodevelopment.Model.Components.Builders.MenuBuilder;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.CustomerTab;
import com.albinodevelopment.Model.Components.Functions.Function;
import com.albinodevelopment.Model.Components.Functions.FunctionManager;
import com.albinodevelopment.Model.Components.Interpreter.IDrinksListInterpreter;
import com.albinodevelopment.Model.Model;
import com.albinodevelopment.Settings.ApplicationSettings;
import com.albinodevelopment.Settings.ISettingsManager;
import com.albinodevelopment.View.Templates.TemplateLoaderFactory;
import com.albinodevelopment.View.Templates.FunctionTemplate;
import com.albinodevelopment.View.Templates.MenuBuilderTemplate;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class MainWindow extends View implements Initializable {

    @Override
    public void updateMenuBuilderWindow(Menu menu) {
        menuBuilderTemplate.generate(menu);
    }

    @Override
    public MenuBuilderTemplate getMenuBuilderWindow() {
        return menuBuilderTemplate;
    }

    @Override
    public FunctionTemplate getFunctionTemplate(Function function) {
        return tabs.get(function);
    }

    public enum Windows {
        menuBuilder,
        settings,
    }

    private ICommandHandler<ControllerCommand> commandHandler;
    private final WindowLoaderFactory windowLoaderFactory;
    private final TemplateLoaderFactory templateLoaderFactory;
    private Window settingsWindow;
    private Window menuBuilderWindow;
    private Window functionWindow;
    private final HashMap<Function, FunctionTemplate> tabs = new HashMap<>();

    private MenuBuilderTemplate menuBuilderTemplate;
    private boolean menuBuilderOpen = false;
    @FXML
    private TabPane tabPane;

    public MainWindow() {
        super();
        windowLoaderFactory = new WindowLoaderFactory();
        templateLoaderFactory = new TemplateLoaderFactory();
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
        openMenuBuilderTab();
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

    private void openMenuBuilderTab() {
        if (menuBuilderOpen == false) {
            menuBuilderOpen = true;
            if (menuBuilderTemplate == null) {
                menuBuilderTemplate = (MenuBuilderTemplate) templateLoaderFactory.getBuilder().getContentController("MenuBuilderTemplateFXML.fxml");
                menuBuilderTemplate.setMain(this);
            }
            Tab tab = generateTab("Menu Builder", false);
            tab.setOnClosed((Event event) -> {
                menuBuilderOpen = false;
            });
            Parent p = menuBuilderTemplate.generate(MenuBuilder.getInstance().get());
            tab.contentProperty().set(p);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } else {
            ConnorLogger.log("Menu builder already open.", ConnorLogger.PriorityLevel.Low);
        }

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
    public Window getWindowByName(Windows windowName) {
        Window window = null;
        switch (windowName) {
            case menuBuilder:
                window = menuBuilderWindow;
                break;
            case settings:
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

    private FunctionTemplate createNewFunctionTemplate(Function function) {
        FunctionTemplate template = (FunctionTemplate) templateLoaderFactory.getBuilder().getContentController("FunctionTemplateFXML.fxml");
        template.setMain(this); 
        template.setContent(function);
        tabs.put(function, template);
        return template;
    }

    private void newTab(Function function) {
        ConnorLogger.log("New Tab!", ConnorLogger.PriorityLevel.Low);
        Tab tab = generateTab(function.getName(), true);
        tab.contentProperty().set(getTemplate(function).generate(function));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @Override
    public void updateTab(Function function) {
        FunctionTemplate templateContent = tabs.get(function);
        if (templateContent == null) {
            newTab(function);
        } else {
            ConnorLogger.log("Updating Tab!", ConnorLogger.PriorityLevel.Low);
            templateContent.update(function);
        }
    }

    private FunctionTemplate getTemplate(Function function) {
        FunctionTemplate templateContent = tabs.get(function);
        if (templateContent == null) {
            templateContent = createNewFunctionTemplate(function);
        }
        return templateContent;
    }

    private Tab generateTab(String name, boolean isFunctionTab) {
        Tab tab = new Tab(name);
        if (isFunctionTab) {
            tab.setOnClosed((Event event) -> {
                Tab source = (Tab) event.getSource();
                closeTab(source.getText());
            });
        }
        return tab;
    }

    private void loadFunctionFromFile(Element root) {
        String functionName = root.getAttributeValue("Name");
        Element meta = root.getChild("Metadata");
        String limitString = meta.getChildText("Limit");
        Double limit = Double.valueOf(limitString);
        CustomerTab drinksTab = rebuildDrinksTab(root.getChild("Tab"), limit);

        commandHandler.getCommandHandler().handle(new ModelCommand.NewFunctionCommand(functionName, drinksTab));
    }

    private CustomerTab rebuildDrinksTab(Element tabElem, double limit) {
        if (tabElem.getName() == "Tab") {
            IDrinksListInterpreter dli
                    = (IDrinksListInterpreter) ApplicationSettings
                            .getInstance().getSetting(
                                    ISettingsManager.settingsList.DrinksListInterpreter).getValue();
            Element drinksTabElem = tabElem.getChild("DrinksList");
            Menu drinksList = dli.interpret(drinksTabElem);
            HashMap<MenuItem, Integer> count = new HashMap<>();
            for (Element e : drinksTabElem.getChildren("Drink")) {
                MenuItem drink = drinksList.GetDrink(e.getChildText("Name"));
                int amount = Integer.valueOf(e.getChildText("Count"));
                count.put(drink, amount);
            }

            CustomerTab dt = new CustomerTab(drinksList, limit, count);
            return dt;
        } else {
            ConnorLogger.log("ERROR: Couldn't find tab XML data.", ConnorLogger.PriorityLevel.Medium);
            return null;
        }
    }
}
