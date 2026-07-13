package orders;

import java.util.ArrayList;
import products.Item;
import users.Employee;
import users.EmployeeRole;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gcrey
 */
public class Order {

    private int id;
    private ArrayList<Item> items;
    private OrderStatus status;
    private Employee seller;
    private Employee cook;

    public Order(int id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.seller = null;
        this.cook = null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Employee getSeller() {
        return seller;
    }

    public boolean setSeller(Employee seller) {
        if (seller == null) {
            return false;
        }

        if (seller.getRole() != EmployeeRole.SELLER) {
            return false;
        }

        this.seller = seller;
        return true;
    }

    public Employee getCook() {
        return cook;
    }

    public boolean setCook(Employee cook) {
        if (cook == null) {
            return false;
        }

        if (cook.getRole() != EmployeeRole.COOK) {
            return false;
        }

        this.cook = cook;
        return true;
    }

    public double calculateTotal() {
        double total = 0;

        for (Item item : items) {
            total += item.getPrice();
        }

        return total;
    }
}
