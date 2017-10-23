package com.local.carl.mybacklog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlr on 10/22/2017.
 */

public class Category {

    //Required for category definition
    private String name;
    private List<Item> itemList;

    //Defining the optional parameters for items of the given category


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Category(String name){
        this.name = name;
    }

    public Category(String name, List<Item> itemList){
        this.name = name;
        this.itemList = itemList;
    }


    public void addItem(Item i){
        this.itemList.add(i);
    }
}
