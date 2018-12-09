/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components.Functions;

import com.albinodevelopment.Logging.ConnorLogger;
import com.albinodevelopment.Model.Components.Menu;
import com.albinodevelopment.Model.Components.CustomerTab;
import com.albinodevelopment.Model.Components.Item;
import com.albinodevelopment.Model.Components.MenuItem;
import com.albinodevelopment.XML.XMLable;
import java.io.Serializable;
import java.util.HashMap;
import org.jdom2.Element;

/**
 *
 * @author conno
 */
public class Function implements Serializable, XMLable {

    private final String name;
    private CustomerTab tab;

    public Function(String name, CustomerTab tab) {
        this.name = name;
        this.tab = tab;
    }

    public String getName() {
        return name;
    }

    public String getLimit() {
        Double d_limit = tab.GetLimit();
        return d_limit.toString();
    }

    public String getCurrentValue() {
        Double d_Current = tab.GetCurrentValue();
        return d_Current.toString();
    }

    public String getPercentUsed() {
        Double d_Percent = tab.GetPercentUsed() * 100;
        d_Percent = CustomerTab.round(d_Percent, 4);
        return d_Percent.toString() + "%";
    }

    public Double getPercentAsDouble() {
        return tab.GetPercentUsed();
    }

    public Menu getMenu() {
        return tab.getMenu();
    }

    public boolean changeMenu(Menu newMenu) {
        boolean canChange = true;
        HashMap<MenuItem, Integer> newCount = new HashMap<>();

        for (MenuItem item : tab.getMenu().getMenuMap().values()) {
            if (newMenu.contains(item)) {
                newMenu.GetDrink(item.getName()).setItemState(Item.ItemState.open);
                newCount.put(newMenu.GetDrink(item.getName()), tab.GetCount(item));
                ConnorLogger.log("Item: " + item.getName().toUpperCase() + " will be added as an unlocked item.", ConnorLogger.PriorityLevel.Medium);
            } else {
                // merge and lock
                if (tab.GetCount(item) != 0) {
                    // merge it 
                    item.setItemState(Item.ItemState.locked);
                    newMenu.add(item);
                    newCount.put(item, tab.GetCount(item));
                    ConnorLogger.log("Item: " + item.getName().toUpperCase() + " has value of " + tab.GetCount(item) + " and will be added as a locked item.", ConnorLogger.PriorityLevel.Medium);
                }
            }
        }
        for (MenuItem item : newMenu.getMenuMap().values()) {
            ConnorLogger.log(item.getName().toUpperCase() + " has a value of " + newCount.get(item), ConnorLogger.PriorityLevel.Medium);
            if (newCount.get(item) == null) {
                newCount.put(item, 0);
            }
        }
        tab = new CustomerTab(newMenu, tab.GetLimit(), newCount);
        return canChange;
    }

    public CustomerTab getTab() {
        return tab;
    }

    @Override
    public Element toXML() {
        Element root = new Element("Function");
        root.setAttribute("Name", this.name);

        Element meta = new Element("Metadata");
        Element limit = new Element("Limit");
        limit.addContent(getLimit());
        meta.addContent(limit);
        root.addContent(meta);

        Element tab = new Element("Tab");
        tab.addContent(this.tab.toXML());
        root.addContent(tab);

        return root;
    }

}
