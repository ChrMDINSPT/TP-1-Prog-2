package users;


import java.util.ArrayList;
import orders.Order;
import orders.OrderStatus;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcrey
 */

public class Inspector extends User {

    private ArrayList<Order> salesOrders;

    public Inspector(String name, int id) {
        super(name, id);
        this.salesOrders = new ArrayList<>();
    }

    public void receiveSalesOrders(ArrayList<Order> orders) {
        salesOrders.clear();

        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.DELIVERED) {
                salesOrders.add(order);
            }
        }
    }

    public void showSalesOrders() {
        for (Order order : salesOrders) {
            System.out.println(order);
        }
    }

    public int countSalesOrders() {
        return salesOrders.size();
    }

    public int countItemsSold() {
        int totalItems = 0;

        for (Order order : salesOrders) {
            totalItems += order.getItems().size();
        }

        return totalItems;
    }

    public void showSalesReport() {
        System.out.println("Ordenes completadas: " + countSalesOrders());
        System.out.println("Items vendidos: " + countItemsSold());
    }
}