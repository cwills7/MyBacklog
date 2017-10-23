package com.local.carl.mybacklog;

/**
 * Created by carlr on 10/22/2017.
 */

public class Item {

    private String name;
    private String desc;
    private int priority;
    private boolean finished;
    private double rating;
    private boolean own;
    private String categoryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isOwn() {
        return own;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }




    public Item (){

    }

    public Item(String name, String desc, int priority){
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.finished = false;
    }
    public Item(String name, String desc, int priority, boolean own){
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.own = true;
        this.finished = false;
    }

    public Item(String name, String desc, int priority, double rating){
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.rating = rating;
        this.finished = false;
    }

    public Item(String name, String desc, int priority, boolean own, double rating){
        this.name = name;
        this.desc = desc;
        this.priority = priority;
        this.own = own;
        this.rating = rating;
        this.finished = false;
    }

    public Item markItemAsDone(Item item){
        item.setFinished(true);
        return item;
    }

    public Item changeItemPriority(Item item, int newPriority){
        item.setPriority(newPriority);
        return item;
    }

}
