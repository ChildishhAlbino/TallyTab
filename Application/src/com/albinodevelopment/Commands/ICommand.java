/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

/**
 *
 * @author conno
 * @param <U>
 */
public interface ICommand<U> {

    enum ExecutionResult {
        success,
        failure,
    }

    // checks to see if the conditions are correct for code packet to be run
    // returns a boolean to see if the command will succeed when run
    boolean CanExecute(U commandHandler);

    // executes code packet
    ExecutionResult Execute(U commandHandler);

}
