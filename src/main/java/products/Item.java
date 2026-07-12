package products;


import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcrey
 */
public class Item {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int price;

    public Item(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.price = 0;
    }

    public Item(String name, ArrayList<Ingredient> ingredients, int price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasIngredients() {
         return !ingredients.isEmpty();
    }
    
    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
    
    public void removeIngredient(Ingredient ingredient){
        this.ingredients.remove(ingredient);
    }
    
    public double getPrice() {
        return price;
    }
}