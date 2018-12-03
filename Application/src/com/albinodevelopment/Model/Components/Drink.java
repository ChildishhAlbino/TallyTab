/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class Drink extends Item implements Serializable, XMLable {

    public Drink(double price, String name) {
        super(price, name);
    }

    @Override
    public Element toXML() {
        Element drinkContainer = new Element("Drink");

        Element nameContainer = new Element("Name");
        nameContainer.addContent(this.name);
        drinkContainer.addContent(nameContainer);

        Element priceContainer = new Element("Price");
        priceContainer.addContent(String.valueOf(this.price));
        drinkContainer.addContent(priceContainer);
        
        return drinkContainer;
    }

}
