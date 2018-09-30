/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Builders;


/**
 *
 * @author conno
 * @param <T>
 */
public interface IComponentManager<T extends IBuildable> { // interface that outlines basic methods from creating a "buildable" object

    void create();

    void open();

    void save();

    T get();
    
    void clear();
}
