/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.IO;

/**
 *
 * @author conno
 * @param <T>
 */
public class SerializerDeserializerFactory<T> {

    public Serializer<T> getSerializer() {
        return new Serializer<>();
    }

    public Deserializer<T> getDeserializer() {
        return new Deserializer<>();
    }
}
