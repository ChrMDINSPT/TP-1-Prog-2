package users;

import orders.Order;
import orders.OrderStatus;
import system.BurgerKingSystem;
import products.Item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gcrey
 */


public class Manager extends User {

    public Manager(String name, int id) {
        super(name, id);
    }

    public boolean addUser(BurgerKingSystem system, User user) {
        return system.addUser(user);
    }

    public boolean removeUser(BurgerKingSystem system, User user) {
        return system.removeUser(user);
    }

    public User findUserById(BurgerKingSystem system, int id) {
        return system.findUserById(id);
    }

    public void showUsers(BurgerKingSystem system) {
        for (User user : system.getUsers()) {
            System.out.println(
                    user.getId() + " - " + user.getName()
            );
        }
    }

    public boolean assignRole(
            Employee employee,
            EmployeeRole role) {

        if (employee == null || role == null) {
            return false;
        }

        employee.setRole(role);
        return true;
    }

    public boolean removeRole(Employee employee) {
        if (employee == null) {
            return false;
        }

        employee.setRole(EmployeeRole.UNASSIGNED);
        return true;
    }

    public void addMenuItem(
            BurgerKingSystem system,
            Item item) {

        system.getMenu().addItem(item);
    }

    public boolean removeMenuItem(
            BurgerKingSystem system,
            Item item) {

        return system.getMenu().removeItem(item);
    }

    public Item findMenuItem(
            BurgerKingSystem system,
            String name) {

        return system.getMenu().findItemByName(name);
    }

    public void showMenu(BurgerKingSystem system) {
        system.getMenu().showMenu();
    }

    public void showAllOrders(BurgerKingSystem system) {
        for (Order order : system.getOrders()) {
            System.out.println(order);
        }
    }

    public boolean cancelOrder(Order order) {
        if (order == null) {
            return false;
        }

        if (order.getStatus() == OrderStatus.DELIVERED
                || order.getStatus() == OrderStatus.CANCELLED) {
            return false;
        }

        order.setStatus(OrderStatus.CANCELLED);
        return true;
    }

    public boolean assignOrderToCook(
            Order order,
            Employee employee) {

        if (order == null || employee == null) {
            return false;
        }

        if (!employee.isCook()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.RECEIVED) {
            return false;
        }

        if (order.getCook() != null) {
            return false;
        }

        order.setCook(employee);
        return true;
    }

    public void sendSalesOrdersToInspector(
            BurgerKingSystem system,
            Inspector inspector) {

        inspector.receiveSalesOrders(
                system.getCompletedOrders()
        );
    }
}
