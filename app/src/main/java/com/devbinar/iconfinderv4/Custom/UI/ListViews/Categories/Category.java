package com.devbinar.iconfinderv4.Custom.UI.ListViews.Categories;

public class Category {

    String name;
    String identifier;

    public Category(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }
}
