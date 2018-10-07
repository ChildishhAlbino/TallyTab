/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

/**
 * public interface that defines the basic methods a command handler must
 * implement in some form.
 *
 * @author conno
 * @param <U> the Type of ICommand this class must implement.
 */
public interface ICommandHandler<U extends ICommand<?>> {

    /**
     * A method that checks if this command handler can handle a certain
     * command.
     *
     * @param command a command of any subclass
     * @return the result of the method as a boolean
     */
    boolean canHandle(Command command);

    /**
     * A method that enables this object to execute or 'handle' command objects.
     *
     * @param command the command that is being handled.
     */
    void handle(U command);

    /**
     * A setter method that allows a subclass to have a command handler set for
     * this object.
     *
     * @param commandHandler The command handler we want to attach to this
     * object.
     */
    void setCommandHandler(ICommandHandler commandHandler);

    /**
     * A getter method for a subclass of this interface.
     *
     * @return the command handler as an ICommandHandler of this class or null.
     */
    ICommandHandler getCommandHandler();
}
