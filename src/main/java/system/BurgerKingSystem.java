/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;
import java.util.ArrayList;
import orders.Order;
import orders.OrderStatus;
import users.User;

/**
 *
 * @author gcrey
 */


public class BurgerKingSystem {

    private ArrayList<User> users;
    private Menu menu;
    private ArrayList<Order> orders;
    private int nextOrderId;

    public BurgerKingSystem() {
        this.users = new ArrayList<>();
        this.menu = new Menu();
        this.orders = new ArrayList<>();
        this.nextOrderId = 1;
    }

    public boolean addUser(User user) {
        if (findUserById(user.getId()) != null) {
            return false;
        }

        users.add(user);
        return true;
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order findOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }

        return null;
    }

    public int generateOrderId() {
        return nextOrderId++;
    }

    public ArrayList<Order> getOrdersByStatus(OrderStatus status) {
        ArrayList<Order> matchingOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getStatus() == status) {
                matchingOrders.add(order);
            }
        }

        return matchingOrders;
    }

    public ArrayList<Order> getPendingOrders() {
        return getOrdersByStatus(OrderStatus.RECEIVED);
    }

    public ArrayList<Order> getCompletedOrders() {
        return getOrdersByStatus(OrderStatus.DELIVERED);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Menu getMenu() {
        return menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}