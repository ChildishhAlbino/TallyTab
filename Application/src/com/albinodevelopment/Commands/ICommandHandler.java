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
public interface ICommandHandler<U extends ICommand<?>> {

    boolean CanHandle(Command command);

    void Handle(U command);

    void SetCommandHandler(ICommandHandler commandHandler);

    ICommandHandler GetCommandHandler();
}
