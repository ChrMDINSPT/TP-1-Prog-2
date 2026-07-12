package users;

import orders.Order;
import orders.OrderStatus;
import products.Item;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcrey
 */
public class Cook extends User {

    public Cook(String name, int id) {
        super(name, id);
    }

    public boolean startPreparingOrder(Order order) {
        if (order.getStatus() != OrderStatus.RECEIVED) {
            return false;
        }

        if (order.getCook() != null) {
            return false;
        }

        order.setCook(this);
        order.setStatus(OrderStatus.IN_PREPARATION);
        return true;
    }

    public void prepareItem(Item item) {
        System.out.println("Preparing item: " + item.getName());
    }

    public boolean markOrderReady(Order order) {
        if (order.getStatus() != OrderStatus.IN_PREPARATION) {
            return false;
        }

        if (order.getCook() != this) {
            return false;
        }

        order.setStatus(OrderStatus.READY);
        return true;
    }
}
