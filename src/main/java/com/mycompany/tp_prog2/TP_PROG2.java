/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.tp_prog2;

/**
 *
 * @author gcrey
 */
import console.ConsoleInterface;
import java.util.ArrayList;
import products.Ingredient;
import products.Item;
import system.BurgerKingSystem;
import users.Employee;
import users.EmployeeRole;
import users.Inspector;
import users.Manager;

public class TP_PROG2 {

    public static void main(String[] args) {

        BurgerKingSystem system = new BurgerKingSystem();

        loadInitialUsers(system);
        loadInitialMenu(system);

        ConsoleInterface console
                = new ConsoleInterface(system);

        console.start();
    }

    private static void loadInitialUsers(
            BurgerKingSystem system) {

        Manager manager
                = new Manager("Laura", 1);

        Employee carlos
                = new Employee("Carlos", 2);

        Employee miguel
                = new Employee("Miguel", 3);

        Employee juliana
                = new Employee("Juliana", 4);

        Inspector inspector
                = new Inspector("Ana", 5);

        system.addUser(manager);
        system.addUser(carlos);
        system.addUser(miguel);
        system.addUser(juliana);
        system.addUser(inspector);

        manager.assignRole(
                carlos,
                EmployeeRole.SELLER
        );

        manager.assignRole(
                miguel,
                EmployeeRole.COOK
        );

        manager.assignRole(
                juliana,
                EmployeeRole.UNASSIGNED
        );
    }

    private static void loadInitialMenu(
            BurgerKingSystem system) {

        Ingredient bread
                = new Ingredient("Pan");

        Ingredient meat
                = new Ingredient("Carne");

        Ingredient cheese
                = new Ingredient("Queso");

        Ingredient lettuce
                = new Ingredient("Lechuga");

        Ingredient tomato
                = new Ingredient("Tomate");

        Ingredient onion
                = new Ingredient("Cebolla");

        ArrayList<Ingredient> cheeseburgerIngredients
                = new ArrayList<>();

        cheeseburgerIngredients.add(bread);
        cheeseburgerIngredients.add(meat);
        cheeseburgerIngredients.add(cheese);

        Item cheeseburger
                = new Item(
                        "Hamburguesa con queso",
                        cheeseburgerIngredients,
                        6500
                );

        ArrayList<Ingredient> completeBurgerIngredients
                = new ArrayList<>();

        completeBurgerIngredients.add(bread);
        completeBurgerIngredients.add(meat);
        completeBurgerIngredients.add(cheese);
        completeBurgerIngredients.add(lettuce);
        completeBurgerIngredients.add(tomato);
        completeBurgerIngredients.add(onion);

        Item completeBurger
                = new Item(
                        "Hamburguesa completa",
                        completeBurgerIngredients,
                        8000
                );

        ArrayList<Ingredient> friesIngredients
                = new ArrayList<>();

        Ingredient potato
                = new Ingredient("Papa");

        Ingredient salt
                = new Ingredient("Sal");

        friesIngredients.add(potato);
        friesIngredients.add(salt);

        Item fries
                = new Item(
                        "Papas fritas",
                        friesIngredients,
                        3500
                );

        system.getMenu().addItem(cheeseburger);
        system.getMenu().addItem(completeBurger);
        system.getMenu().addItem(fries);
    }
}
