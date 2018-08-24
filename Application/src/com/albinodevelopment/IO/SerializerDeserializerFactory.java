/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

import java.io.Serializable;

/**
 *
 * @author conno
 * @param <T>
 */
public class SerializerDeserializerFactory {

    public <T extends Serializable> Serializer<T> getSerializer(Class<T> classPath) {
        return new Serializer<>();
    }

    public <T extends Serializable> Deserializer<T> getDeserializer(Class<T> classPath) {
        return new Deserializer<>();
    }
}
