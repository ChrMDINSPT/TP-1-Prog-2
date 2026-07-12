/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp_prog2;

/**
 *
 * @author gcrey
 */


import orders.Order;
import products.Ingredient;
import products.Item;
import system.BurgerKingSystem;
import users.Cook;
import users.Inspector;
import users.Manager;
import users.Seller;

public class TP_PROG2 {

    public static void main(String[] args) {

        BurgerKingSystem system = new BurgerKingSystem();

        Manager manager = new Manager("Laura", 1);
        Seller seller = new Seller("Carlos", 2);
        Cook cook = new Cook("Miguel", 3);
        Inspector inspector = new Inspector("Ana", 4);

        manager.addUser(system, manager);
        manager.addUser(system, seller);
        manager.addUser(system, cook);
        manager.addUser(system, inspector);

        Ingredient bread = new Ingredient("Bread");
        Ingredient meat = new Ingredient("Meat");
        Ingredient cheese = new Ingredient("Cheese");

        Item cheeseburger = new Item("Cheeseburger");

        cheeseburger.addIngredient(bread);
        cheeseburger.addIngredient(meat);
        cheeseburger.addIngredient(cheese);

        manager.addMenuItem(system, cheeseburger);

        system.getMenu().showMenu();

        Order order = seller.createOrder(system);

        seller.addItemToOrder(order, cheeseburger);

        boolean submitted = seller.submitOrder(order, system);

        if (submitted) {
            System.out.println("Order submitted.");
        }

        boolean assigned = manager.assignOrderToCook(order, cook);

        if (assigned) {
            System.out.println("Order assigned to cook.");
        }

        boolean preparing = cook.startPreparingOrder(order);

        if (preparing) {
            for (Item item : order.getItems()) {
                cook.prepareItem(item);
            }
        }

        boolean ready = cook.markOrderReady(order);

        if (ready) {
            System.out.println("Order is ready.");
        }

        boolean delivered = seller.deliverOrder(order);

        if (delivered) {
            System.out.println("Order delivered.");
        }

        manager.sendSalesOrdersToInspector(system, inspector);

        inspector.showSalesReport();
    }
}
