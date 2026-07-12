package users;

import products.Item;
import orders.Order;
import orders.OrderStatus;
import system.BurgerKingSystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcrey
 */


public class Seller extends User {

    public Seller(String name, int id) {
        super(name, id);
    }

    public Order createOrder(BurgerKingSystem system) {
        int orderId = system.generateOrderId();
        return new Order(orderId);
    }

    public void addItemToOrder(Order order, Item item) {
        order.addItem(item);
    }

    public void removeItemFromOrder(Order order, Item item) {
        order.removeItem(item);
    }

    public boolean submitOrder(Order order, BurgerKingSystem system) {
        if (order.isEmpty()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            return false;
        }

        order.setStatus(OrderStatus.RECEIVED);
        system.addOrder(order);
        return true;
    }

    public boolean deliverOrder(Order order) {
        if (order.getStatus() != OrderStatus.READY) {
            return false;
        }

        order.setStatus(OrderStatus.DELIVERED);
        return true;
    }
}