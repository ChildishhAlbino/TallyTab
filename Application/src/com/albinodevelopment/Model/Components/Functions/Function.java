/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Functions;

import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.CustomerTab;
import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class Function implements Serializable, XMLable {

    private final String name;
    private final CustomerTab drinksTab;

    public Function(String name, CustomerTab tab) {
        this.name = name;
        this.drinksTab = tab;
    }
    
    public String getName() {
        return name;
    }

    public String getLimit() {
        Double d_limit = drinksTab.GetLimit();
        return d_limit.toString();
    }

    public String getCurrentValue() {
        Double d_Current = drinksTab.GetCurrentValue();
        return d_Current.toString();
    }

    public String getPercentUsed() {
        Double d_Percent = drinksTab.GetPercentUsed() * 100;
        d_Percent = CustomerTab.round(d_Percent, 4);
        return d_Percent.toString() + "%";
    }

    public Double getPercentAsDouble() {
        return drinksTab.GetPercentUsed();
    }

    public Menu getDrinksList() {
        return drinksTab.getMenu();
    }

    public CustomerTab getDrinksTab() {
        return drinksTab;
    }

    @Override
    public Element toXML() {
        Element root = new Element("Function");
        root.setAttribute("Name", this.name);
        
        Element meta = new Element("Metadata");
        Element limit = new Element ("Limit");
        limit.addContent(getLimit());
        meta.addContent(limit);
        root.addContent(meta);
        
        Element tab = new Element("Tab");
        tab.addContent(drinksTab.toXML());
        root.addContent(tab);
        
        return root;
    }   

}
