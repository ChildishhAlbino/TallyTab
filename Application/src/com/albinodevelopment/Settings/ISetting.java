/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Settings;

/**
 *
 * @author conno
 * @param <U>
 */
public interface ISetting<U> {

    boolean change(U value);

    U getValue();

    void setToDefault();
}
