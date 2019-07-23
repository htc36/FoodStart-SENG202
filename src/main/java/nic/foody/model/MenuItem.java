package nic.foody.model;

import java.util.List;

/**
 * A class to keep track of menu items and their ingredients. Only a sample --
 * needs lots more detail -- such as some methods :-)
 */
public class MenuItem {
    private String code;
    private String name;
    // just the ingredient names
    private List<String> ingredients;

    /**
     * Constructor for class.
     */
    public MenuItem(String code, String name, List<String> ingredients) {
        this.code = code;
        this.name = name;
        this.ingredients = ingredients;
    }

    public void addIngredient(String it) {
        ingredients.add(it);
    }

    public List<String> ingredientNames() {
        return ingredients;
    }

    public String ingredients() {
        String recipeText;
        recipeText = "[" + code + "(" + name + "):";
        for(String s:ingredients) {
            recipeText += " " + s;
        }
        recipeText += "]";
        return recipeText;
    }
}