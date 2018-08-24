/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.View;

/**
 *
 * @author conno
 * @param <T>
 */
public class WindowLoaderFactory {

    public <T extends Window> WindowLoader<T> getWindowLoader(Class<T> windowClass) {
        return new WindowLoader<>();
    }
}
