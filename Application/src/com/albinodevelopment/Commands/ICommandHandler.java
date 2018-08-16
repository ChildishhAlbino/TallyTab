/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Commands;

/**
 *
 * @author conno
 */
public interface ICommandHandler<U extends ICommand<?>> {

    boolean CanHandle(U command);

    void Handle(U command);

    void SetCommandHandler(ICommandHandler commandHandler);
}
