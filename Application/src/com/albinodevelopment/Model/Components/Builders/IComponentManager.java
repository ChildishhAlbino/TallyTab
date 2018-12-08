/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Builders;

/**
 * interface that outlines basic methods from creating a "buildable" object
 *
 * @author conno
 * @param <T>
 */
public interface IComponentManager<T extends IBuildable> { // 

    void create();

    void open();

    boolean save();

    T get();

    void clear();

    boolean validate(String path);

    T openAndGet(String path);
}
