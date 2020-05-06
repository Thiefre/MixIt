package com.example.recipe;

public class RecipeItem {
    private int id;
    private String recipeName;
    private String ingredients;
    private String method;
    private byte[] thumbnail;
    private byte[] mainImg;
    private int count;

    public int get_id() {
        return id;
    }

    public String get_recipeName() {
        return recipeName;
    }

    public String get_ingredients() {
        return ingredients;
    }

    public String get_Method() {
        return method;
    }

    public byte[] get_thumbnail() {
        return thumbnail;
    }

    public byte[] get_mainImg() {
        return mainImg;
    }

    public int getCount()
    {
        return count;
    }

    public RecipeItem(int id, String recipeName, String ingredients,
                      String method, byte[] thumbnail, byte[] mainImg, int count) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.method = method;
        this.thumbnail = thumbnail;
        this.mainImg = mainImg;
        this.count = count;
    }
}
