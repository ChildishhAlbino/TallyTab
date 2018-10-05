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
public abstract class Command<U extends ICommandHandler<?>> implements ICommand<U> {

    protected String errorCode;

    @Override
    public String GetErrorCode() {
        return errorCode;
    }

    @Override
    public void GenerateErrorCode(String errorCode) {
        this.errorCode = this.toString() + errorCode;
    }

    @Override
    public String toString() {
        String toString = this.getClass().getName() + ": ";
        return toString;
    }

    @Override
    public boolean CanExecute(U commandHandler) {
        return commandHandler.CanHandle(this);
    }

}
