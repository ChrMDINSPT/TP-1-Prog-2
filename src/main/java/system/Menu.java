/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;


import java.util.ArrayList;
import products.Item;

/**
 *
 * @author gcrey
 */


import java.util.ArrayList;

public class Menu {

    private ArrayList<Item> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Item findItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void showMenu() {
        if (items.isEmpty()) {
            System.out.println("El menú está vacío.");
            return;
        }

        System.out.println("Menú disponible:");

        for (Item item : items) {
            System.out.println("- " + item.getName());
        }
    }
}