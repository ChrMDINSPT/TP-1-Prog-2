package products;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcrey
 */
public class Ingredient {
    String name;
    
    public Ingredient(String name) {
    this.name = name;
    }
    public void setName(Ingredient ingredient, String name){
    this.name = name;
    }
    public String getName(Ingredient ingredient){
        return name;
    }
}
