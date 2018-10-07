/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

/**
 * Public abstract class that handles a lot of the generic methods all commands
 * should have.
 *
 * @author conno
 * @param <U> The type of this command's command handler.
 */
public abstract class Command<U extends ICommandHandler<?>> implements ICommand<U> {

    /**
     * reference variable of a command's error code.
     */
    protected String errorCode;

    /**
     * Implementation of ICommand's getErrorCode.
     *
     * @return a command's errorCode as a String.
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Implementation of the ICommand's generateErrorCode. Formats an identifier
     * of the command with the provided string.
     *
     * @param errorCode the error code message you want the command to output in
     * the case of failure.
     */
    @Override
    public void generateErrorCode(String errorCode) {
        this.errorCode = this.toString() + errorCode;
    }

    /**
     * Override of toString()
     *
     * @return The name of the command class plus a colon and a space.
     */
    @Override
    public String toString() {
        String toString = this.getClass().getName() + ": ";
        return toString;
    }

    /**
     * Default Implementation of ICommand's canExecute 
     * 
     * @param commandHandler the command handler that is calling this method.
     * @return the result of the command handler's canHandle as a boolean.
     */
    @Override
    public boolean canExecute(U commandHandler) {
        return commandHandler.canHandle(this);
    }

}
