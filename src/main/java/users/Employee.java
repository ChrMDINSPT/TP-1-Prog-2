/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;
import orders.Order;
import orders.OrderStatus;
import products.Item;
import system.BurgerKingSystem;

/**
 *
 * @author gcrey
 */




public class Employee extends User {

    private EmployeeRole role;

    public Employee(String name, int id) {
        super(name, id);
        this.role = EmployeeRole.UNASSIGNED;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public boolean isSeller() {
        return role == EmployeeRole.SELLER;
    }

    public boolean isCook() {
        return role == EmployeeRole.COOK;
    }

    // Operaciones vendedor

    public Order createOrder(BurgerKingSystem system) {
        if (!isSeller()) {
            return null;
        }

        int orderId = system.generateOrderId();
        Order order = new Order(orderId);

        order.setSeller(this);

        return order;
    }

    public boolean addItemToOrder(Order order, Item item) {
        if (!isSeller()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            return false;
        }

        order.addItem(item);
        return true;
    }

    public boolean removeItemFromOrder(Order order, Item item) {
        if (!isSeller()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            return false;
        }

        return order.removeItem(item);
    }

    public boolean submitOrder(
            Order order,
            BurgerKingSystem system) {

        if (!isSeller()) {
            return false;
        }

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
        if (!isSeller()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.READY) {
            return false;
        }

        order.setStatus(OrderStatus.DELIVERED);
        return true;
    }

    // Operaciones cocinero

    public boolean startPreparingOrder(Order order) {
        if (!isCook()) {
            return false;
        }

        if (order.getStatus() != OrderStatus.RECEIVED) {
            return false;
        }

        if (order.getCook() != null
                && order.getCook() != this) {
            return false;
        }

        order.setCook(this);
        order.setStatus(OrderStatus.IN_PREPARATION);

        return true;
    }

    public void prepareItem(Item item) {
        if (!isCook()) {
            System.out.println(
                    "Este empleado no es cocinero"
            );
            return;
        }

        System.out.println(
                "Preparando item: " + item.getName()
        );
    }

    public boolean markOrderReady(Order order) {
        if (!isCook()) {
            return false;
        }

        if (order.getStatus()
                != OrderStatus.IN_PREPARATION) {
            return false;
        }

        if (order.getCook() != this) {
            return false;
        }

        order.setStatus(OrderStatus.READY);
        return true;
    }
}