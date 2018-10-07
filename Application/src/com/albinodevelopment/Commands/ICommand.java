/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

/**
 * Public interface that defines the basic methods all command must have.
 * @author conno
 * @param <U> The type of this command's command handler.
 */
public interface ICommand<U> {

    /**
     * Enumeration that stores the results of a command's execution.
     */
    enum ExecutionResult {
        success,
        failure,
    }

    /**
     * Boolean that checks certain parameters or state of the command handler to
     * check if the command can be executed. e.g. if arguments are null - this
     * could return null.
     *
     * @param commandHandler the command handler that is running this method.
     * @return the result of the function as a boolean.
     */
    boolean canExecute(U commandHandler);

    /**
     * The code packet of the command pattern. Calls methods on the command
     * handler, passing arguments that the command received through its
     * constructor
     *
     * @param commandHandler the command handler that is running this method.
     * @return the result of the method as an ExecutionResult.
     */
    ExecutionResult execute(U commandHandler);

    /**
     * A public string that returns the error code if there is one.
     * @return the error code as a String.
     */
    String getErrorCode();
     /**
      * a public method that is used to generate error codes if something goes wrong
      * while executing the command's orders.
      * 
      * @param errorCode the string to store as an error code. 
      */
    void generateErrorCode(String errorCode);
}
