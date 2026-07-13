/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package console;

import java.util.ArrayList;
import java.util.Scanner;
import orders.Order;
import orders.OrderStatus;
import products.Ingredient;
import products.Item;
import system.BurgerKingSystem;
import users.Employee;
import users.EmployeeRole;
import users.Inspector;
import users.Manager;
import users.User;
/**
 *
 * @author gcrey
 */


public class ConsoleInterface {

    private final BurgerKingSystem system;
    private final Scanner scanner;

    public ConsoleInterface(BurgerKingSystem system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMainMenu();

            int option = readInteger();

            switch (option) {
                case 1:
                    login();
                    break;

                case 0:
                    running = false;
                    System.out.println(
                            "Gracias por utilizar el sistema."
                    );
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println("================================");
        System.out.println(" SISTEMA DE GESTIÓN DEL LOCAL");
        System.out.println("================================");
        System.out.println("1. Iniciar sesión");
        System.out.println("0. Salir");
        System.out.print("Elegí una opción: ");
    }

    private void login() {
        System.out.print("Ingresá tu ID de usuario: ");
        int id = readInteger();

        User user = system.findUserById(id);

        if (user == null) {
            System.out.println(
                    "No se encontró ningún usuario con ese ID."
            );
            return;
        }

        System.out.println();
        System.out.println(
                "Bienvenido/a, " + user.getName() + "."
        );

        if (user instanceof Manager) {
            showManagerMenu((Manager) user);
            return;
        }

        if (user instanceof Inspector) {
            showInspectorMenu((Inspector) user);
            return;
        }

        if (user instanceof Employee) {
            Employee employee = (Employee) user;

            if (employee.isSeller()) {
                showSellerMenu(employee);
            } else if (employee.isCook()) {
                showCookMenu(employee);
            } else {
                System.out.println(
                        "Todavía no tenés un rol asignado para hoy."
                );
            }

            return;
        }

        System.out.println(
                "El usuario no tiene un tipo de acceso reconocido."
        );
    }

    // =========================================================
    // MENÚ DE GERENTE
    // =========================================================

    private void showManagerMenu(Manager manager) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("========================");
            System.out.println(" MENÚ DE GERENTE");
            System.out.println("========================");
            System.out.println("1. Ver usuarios");
            System.out.println("2. Agregar empleado");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Asignar rol diario");
            System.out.println("5. Ver menú de productos");
            System.out.println("6. Agregar producto al menú");
            System.out.println("7. Eliminar producto del menú");
            System.out.println("8. Ver todos los pedidos");
            System.out.println("9. Asignar pedido a un cocinero");
            System.out.println("10. Cancelar pedido");
            System.out.println("11. Enviar ventas a un inspector");
            System.out.println("0. Cerrar sesión");
            System.out.print("Elegí una opción: ");

            int option = readInteger();

            switch (option) {
                case 1:
                    manager.showUsers(system);
                    break;

                case 2:
                    addEmployee(manager);
                    break;

                case 3:
                    removeUser(manager);
                    break;

                case 4:
                    assignDailyRole(manager);
                    break;

                case 5:
                    manager.showMenu(system);
                    break;

                case 6:
                    addMenuItem(manager);
                    break;

                case 7:
                    removeMenuItem(manager);
                    break;

                case 8:
                    showAllOrders();
                    break;

                case 9:
                    assignOrderToCook(manager);
                    break;

                case 10:
                    cancelOrder(manager);
                    break;

                case 11:
                    sendSalesToInspector(manager);
                    break;

                case 0:
                    running = false;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void addEmployee(Manager manager) {
        System.out.print("Ingresá el nombre del empleado: ");
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            System.out.println(
                    "El nombre no puede estar vacío."
            );
            return;
        }

        System.out.print("Ingresá el ID del empleado: ");
        int id = readInteger();

        Employee employee = new Employee(name, id);

        boolean added = manager.addUser(system, employee);

        if (added) {
            System.out.println(
                    "Empleado agregado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo agregar el empleado. "
                    + "Ya existe un usuario con ese ID."
            );
        }
    }

    private void removeUser(Manager manager) {
        showUsersBriefly();

        System.out.print(
                "Ingresá el ID del usuario que querés eliminar: "
        );

        int id = readInteger();

        User user = system.findUserById(id);

        if (user == null) {
            System.out.println(
                    "No se encontró ningún usuario con ese ID."
            );
            return;
        }

        if (user == manager) {
            System.out.println(
                    "No podés eliminar tu propio usuario "
                    + "mientras tenés la sesión iniciada."
            );
            return;
        }

        boolean removed = manager.removeUser(system, user);

        if (removed) {
            System.out.println(
                    "Usuario eliminado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo eliminar el usuario."
            );
        }
    }

    private void assignDailyRole(Manager manager) {
        showEmployees();

        System.out.print(
                "Ingresá el ID del empleado: "
        );

        int id = readInteger();

        User user = system.findUserById(id);

        if (!(user instanceof Employee)) {
            System.out.println(
                    "El usuario indicado no es un empleado."
            );
            return;
        }

        Employee employee = (Employee) user;

        System.out.println();
        System.out.println("1. Vendedor");
        System.out.println("2. Cocinero");
        System.out.println("3. Sin asignar");
        System.out.print("Elegí el rol diario: ");

        int option = readInteger();
        EmployeeRole role;

        switch (option) {
            case 1:
                role = EmployeeRole.SELLER;
                break;

            case 2:
                role = EmployeeRole.COOK;
                break;

            case 3:
                role = EmployeeRole.UNASSIGNED;
                break;

            default:
                showInvalidOption();
                return;
        }

        boolean assigned =
                manager.assignRole(employee, role);

        if (assigned) {
            System.out.println(
                    "Rol asignado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo asignar el rol."
            );
        }
    }

    private void addMenuItem(Manager manager) {
        System.out.print("Ingresá el nombre del producto: ");
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            System.out.println(
                    "El nombre del producto no puede estar vacío."
            );
            return;
        }

        if (system.getMenu().findItemByName(name) != null) {
            System.out.println(
                    "Ya existe un producto con ese nombre."
            );
            return;
        }

        System.out.print("Ingresá el precio del producto: ");
        int price = readInteger();

        if (price < 0) {
            System.out.println(
                    "El precio no puede ser negativo."
            );
            return;
        }

        ArrayList<Ingredient> ingredients =
                readIngredients();

        Item item = new Item(
                name,
                ingredients,
                price
        );

        manager.addMenuItem(system, item);

        System.out.println(
                "Producto agregado al menú correctamente."
        );
    }

    private ArrayList<Ingredient> readIngredients() {
        ArrayList<Ingredient> ingredients =
                new ArrayList<>();

        boolean adding = true;

        while (adding) {
            System.out.print(
                    "Ingresá un ingrediente "
                    + "o dejá el campo vacío para terminar: "
            );

            String ingredientName =
                    scanner.nextLine().trim();

            if (ingredientName.isBlank()) {
                adding = false;
            } else {
                ingredients.add(
                        new Ingredient(ingredientName)
                );

                System.out.println(
                        "Ingrediente agregado."
                );
            }
        }

        return ingredients;
    }

    private void removeMenuItem(Manager manager) {
        system.getMenu().showMenu();

        if (system.getMenu().isEmpty()) {
            return;
        }

        System.out.print(
                "Ingresá el nombre del producto "
                + "que querés eliminar: "
        );

        String name = scanner.nextLine().trim();

        Item item =
                system.getMenu().findItemByName(name);

        if (item == null) {
            System.out.println(
                    "No se encontró ese producto."
            );
            return;
        }

        boolean removed =
                manager.removeMenuItem(system, item);

        if (removed) {
            System.out.println(
                    "Producto eliminado del menú."
            );
        } else {
            System.out.println(
                    "No se pudo eliminar el producto."
            );
        }
    }

    private void assignOrderToCook(Manager manager) {
        ArrayList<Order> pendingOrders =
                system.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println(
                    "No hay pedidos pendientes de asignación."
            );
            return;
        }

        showOrders(pendingOrders);
        showCooks();

        System.out.print("Ingresá el número del pedido: ");
        int orderId = readInteger();

        Order order = system.findOrderById(orderId);

        if (order == null) {
            System.out.println(
                    "No se encontró ese pedido."
            );
            return;
        }

        System.out.print(
                "Ingresá el ID del cocinero: "
        );

        int cookId = readInteger();

        User user = system.findUserById(cookId);

        if (!(user instanceof Employee)) {
            System.out.println(
                    "El usuario indicado no es un empleado."
            );
            return;
        }

        Employee employee = (Employee) user;

        boolean assigned =
                manager.assignOrderToCook(
                        order,
                        employee
                );

        if (assigned) {
            System.out.println(
                    "Pedido asignado al cocinero."
            );
        } else {
            System.out.println(
                    "No se pudo asignar el pedido. "
                    + "Verificá que el empleado tenga "
                    + "el rol de cocinero y que el pedido "
                    + "todavía no esté asignado."
            );
        }
    }

    private void cancelOrder(Manager manager) {
        showAllOrders();

        System.out.print(
                "Ingresá el número del pedido "
                + "que querés cancelar: "
        );

        int orderId = readInteger();

        Order order = system.findOrderById(orderId);

        if (order == null) {
            System.out.println(
                    "No se encontró ese pedido."
            );
            return;
        }

        boolean cancelled =
                manager.cancelOrder(order);

        if (cancelled) {
            System.out.println(
                    "Pedido cancelado correctamente."
            );
        } else {
            System.out.println(
                    "No se puede cancelar ese pedido."
            );
        }
    }

    private void sendSalesToInspector(Manager manager) {
        ArrayList<Inspector> inspectors =
                getInspectors();

        if (inspectors.isEmpty()) {
            System.out.println(
                    "No hay inspectores registrados."
            );
            return;
        }

        System.out.println("Inspectores disponibles:");

        for (Inspector inspector : inspectors) {
            System.out.println(
                    inspector.getId()
                    + " - "
                    + inspector.getName()
            );
        }

        System.out.print(
                "Ingresá el ID del inspector: "
        );

        int id = readInteger();

        User user = system.findUserById(id);

        if (!(user instanceof Inspector)) {
            System.out.println(
                    "El usuario indicado no es un inspector."
            );
            return;
        }

        Inspector inspector = (Inspector) user;

        manager.sendSalesOrdersToInspector(
                system,
                inspector
        );

        System.out.println(
                "Listado de ventas enviado al inspector."
        );
    }

    // =========================================================
    // MENÚ DE VENDEDOR
    // =========================================================

    private void showSellerMenu(Employee seller) {
        Order currentOrder = null;
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("========================");
            System.out.println(" MENÚ DE VENDEDOR");
            System.out.println("========================");
            System.out.println("1. Ver menú de productos");
            System.out.println("2. Crear un pedido");
            System.out.println("3. Agregar ítem al pedido");
            System.out.println("4. Quitar ítem del pedido");
            System.out.println("5. Personalizar un ítem");
            System.out.println("6. Ver pedido actual");
            System.out.println("7. Enviar pedido");
            System.out.println("8. Entregar pedido listo");
            System.out.println("0. Cerrar sesión");
            System.out.print("Elegí una opción: ");

            int option = readInteger();

            switch (option) {
                case 1:
                    system.getMenu().showMenu();
                    break;

                case 2:
                    if (currentOrder != null) {
                        System.out.println(
                                "Ya tenés un pedido en preparación."
                        );
                    } else {
                        currentOrder =
                                seller.createOrder(system);

                        if (currentOrder != null) {
                            System.out.println(
                                    "Pedido creado con el número "
                                    + currentOrder.getId()
                                    + "."
                            );
                        } else {
                            System.out.println(
                                    "No se pudo crear el pedido."
                            );
                        }
                    }
                    break;

                case 3:
                    addItemToCurrentOrder(
                            seller,
                            currentOrder
                    );
                    break;

                case 4:
                    removeItemFromCurrentOrder(
                            seller,
                            currentOrder
                    );
                    break;

                case 5:
                    customizeOrderItem(currentOrder);
                    break;

                case 6:
                    showOrder(currentOrder);
                    break;

                case 7:
                    if (submitCurrentOrder(
                            seller,
                            currentOrder
                    )) {
                        currentOrder = null;
                    }
                    break;

                case 8:
                    deliverReadyOrder(seller);
                    break;

                case 0:
                    running = false;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void addItemToCurrentOrder(
            Employee seller,
            Order order) {

        if (order == null) {
            System.out.println(
                    "Primero tenés que crear un pedido."
            );
            return;
        }

        system.getMenu().showMenu();

        if (system.getMenu().isEmpty()) {
            return;
        }

        System.out.print(
                "Ingresá el nombre del producto: "
        );

        String name = scanner.nextLine().trim();

        Item menuItem =
                system.getMenu().findItemByName(name);

        if (menuItem == null) {
            System.out.println(
                    "No se encontró ese producto."
            );
            return;
        }

        Item orderedItem = copyItem(menuItem);

        boolean added =
                seller.addItemToOrder(
                        order,
                        orderedItem
                );

        if (added) {
            System.out.println(
                    "Producto agregado al pedido."
            );
        } else {
            System.out.println(
                    "No se pudo agregar el producto."
            );
        }
    }

    private Item copyItem(Item original) {
        ArrayList<Ingredient> ingredientCopies =
                new ArrayList<>();

        for (Ingredient ingredient :
                original.getIngredients()) {

            ingredientCopies.add(
                    new Ingredient(
                            ingredient.getName()
                    )
            );
        }

        return new Item(
                original.getName(),
                ingredientCopies,
                original.getPrice()
        );
    }

    private void removeItemFromCurrentOrder(
            Employee seller,
            Order order) {

        if (order == null) {
            System.out.println(
                    "Primero tenés que crear un pedido."
            );
            return;
        }

        if (order.isEmpty()) {
            System.out.println(
                    "El pedido no tiene ítems."
            );
            return;
        }

        showOrder(order);

        System.out.print(
                "Ingresá el número del ítem "
                + "que querés quitar: "
        );

        int index = readInteger() - 1;

        if (index < 0
                || index >= order.getItems().size()) {

            System.out.println(
                    "El número de ítem no es válido."
            );
            return;
        }

        Item item = order.getItems().get(index);

        boolean removed =
                seller.removeItemFromOrder(
                        order,
                        item
                );

        if (removed) {
            System.out.println(
                    "Ítem eliminado del pedido."
            );
        } else {
            System.out.println(
                    "No se pudo eliminar el ítem."
            );
        }
    }

    private void customizeOrderItem(Order order) {
        if (order == null) {
            System.out.println(
                    "Primero tenés que crear un pedido."
            );
            return;
        }

        if (order.isEmpty()) {
            System.out.println(
                    "El pedido no tiene ítems."
            );
            return;
        }

        showOrder(order);

        System.out.print(
                "Ingresá el número del ítem "
                + "que querés personalizar: "
        );

        int index = readInteger() - 1;

        if (index < 0
                || index >= order.getItems().size()) {

            System.out.println(
                    "El número de ítem no es válido."
            );
            return;
        }

        Item item = order.getItems().get(index);

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println(
                    "Personalizando: " + item.getName()
            );
            System.out.println("1. Ver ingredientes");
            System.out.println("2. Agregar ingrediente");
            System.out.println("3. Quitar ingrediente");
            System.out.println("0. Terminar");
            System.out.print("Elegí una opción: ");

            int option = readInteger();

            switch (option) {
                case 1:
                    showIngredients(item);
                    break;

                case 2:
                    addIngredientToItem(item);
                    break;

                case 3:
                    removeIngredientFromItem(item);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void showIngredients(Item item) {
        if (!item.hasIngredients()) {
            System.out.println(
                    "El ítem no tiene ingredientes."
            );
            return;
        }

        System.out.println("Ingredientes:");

        for (int i = 0;
                i < item.getIngredients().size();
                i++) {

            Ingredient ingredient =
                    item.getIngredients().get(i);

            System.out.println(
                    (i + 1)
                    + ". "
                    + ingredient.getName()
            );
        }
    }

    private void addIngredientToItem(Item item) {
        System.out.print(
                "Ingresá el nombre del ingrediente: "
        );

        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            System.out.println(
                    "El nombre no puede estar vacío."
            );
            return;
        }

        item.addIngredient(new Ingredient(name));

        System.out.println(
                "Ingrediente agregado al ítem."
        );
    }

    private void removeIngredientFromItem(Item item) {
        showIngredients(item);

        if (!item.hasIngredients()) {
            return;
        }

        System.out.print(
                "Ingresá el número del ingrediente "
                + "que querés quitar: "
        );

        int index = readInteger() - 1;

        if (index < 0
                || index >= item.getIngredients().size()) {

            System.out.println(
                    "El número de ingrediente no es válido."
            );
            return;
        }

        Ingredient ingredient =
                item.getIngredients().get(index);

        item.removeIngredient(ingredient);

        System.out.println(
                "Ingrediente quitado del ítem."
        );
    }

    private boolean submitCurrentOrder(
            Employee seller,
            Order order) {

        if (order == null) {
            System.out.println(
                    "Primero tenés que crear un pedido."
            );
            return false;
        }

        boolean submitted =
                seller.submitOrder(order, system);

        if (submitted) {
            System.out.println(
                    "Pedido enviado correctamente."
            );
            return true;
        }

        System.out.println(
                "No se pudo enviar el pedido. "
                + "Verificá que tenga al menos un ítem."
        );

        return false;
    }

    private void deliverReadyOrder(Employee seller) {
        ArrayList<Order> readyOrders =
                system.getOrdersByStatus(
                        OrderStatus.READY
                );

        if (readyOrders.isEmpty()) {
            System.out.println(
                    "No hay pedidos listos para entregar."
            );
            return;
        }

        showOrders(readyOrders);

        System.out.print(
                "Ingresá el número del pedido "
                + "que querés entregar: "
        );

        int id = readInteger();

        Order order = system.findOrderById(id);

        if (order == null) {
            System.out.println(
                    "No se encontró ese pedido."
            );
            return;
        }

        boolean delivered =
                seller.deliverOrder(order);

        if (delivered) {
            System.out.println(
                    "Pedido entregado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo entregar el pedido."
            );
        }
    }

    // =========================================================
    // MENÚ DE COCINERO
    // =========================================================

    private void showCookMenu(Employee cook) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("========================");
            System.out.println(" MENÚ DE COCINERO");
            System.out.println("========================");
            System.out.println("1. Ver pedidos pendientes");
            System.out.println("2. Comenzar a preparar un pedido");
            System.out.println("3. Ver pedidos en preparación");
            System.out.println("4. Marcar pedido como listo");
            System.out.println("0. Cerrar sesión");
            System.out.print("Elegí una opción: ");

            int option = readInteger();

            switch (option) {
                case 1:
                    showPendingOrders();
                    break;

                case 2:
                    startPreparingOrder(cook);
                    break;

                case 3:
                    showOrdersBeingPrepared(cook);
                    break;

                case 4:
                    markOrderReady(cook);
                    break;

                case 0:
                    running = false;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void showPendingOrders() {
        ArrayList<Order> pendingOrders =
                system.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println(
                    "No hay pedidos pendientes."
            );
            return;
        }

        showOrders(pendingOrders);
    }

    private void startPreparingOrder(Employee cook) {
        ArrayList<Order> pendingOrders =
                system.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println(
                    "No hay pedidos pendientes."
            );
            return;
        }

        ArrayList<Order> assignedOrders =
                new ArrayList<>();

        for (Order order : pendingOrders) {
            if (order.getCook() == cook) {
                assignedOrders.add(order);
            }
        }

        if (assignedOrders.isEmpty()) {
            System.out.println(
                    "No tenés pedidos asignados."
            );
            return;
        }

        showOrders(assignedOrders);

        System.out.print(
                "Ingresá el número del pedido "
                + "que vas a preparar: "
        );

        int id = readInteger();

        Order order = system.findOrderById(id);

        if (order == null) {
            System.out.println(
                    "No se encontró ese pedido."
            );
            return;
        }

        boolean started =
                cook.startPreparingOrder(order);

        if (!started) {
            System.out.println(
                    "No se pudo comenzar a preparar "
                    + "el pedido."
            );
            return;
        }

        System.out.println(
                "Se comenzó a preparar el pedido."
        );

        for (Item item : order.getItems()) {
            cook.prepareItem(item);
        }
    }

    private void showOrdersBeingPrepared(
            Employee cook) {

        ArrayList<Order> ordersBeingPrepared =
                new ArrayList<>();

        for (Order order : system.getOrders()) {
            if (order.getStatus()
                    == OrderStatus.IN_PREPARATION
                    && order.getCook() == cook) {

                ordersBeingPrepared.add(order);
            }
        }

        if (ordersBeingPrepared.isEmpty()) {
            System.out.println(
                    "No tenés pedidos en preparación."
            );
            return;
        }

        showOrders(ordersBeingPrepared);
    }

    private void markOrderReady(Employee cook) {
        ArrayList<Order> ordersBeingPrepared =
                new ArrayList<>();

        for (Order order : system.getOrders()) {
            if (order.getStatus()
                    == OrderStatus.IN_PREPARATION
                    && order.getCook() == cook) {

                ordersBeingPrepared.add(order);
            }
        }

        if (ordersBeingPrepared.isEmpty()) {
            System.out.println(
                    "No tenés pedidos en preparación."
            );
            return;
        }

        showOrders(ordersBeingPrepared);

        System.out.print(
                "Ingresá el número del pedido "
                + "que está listo: "
        );

        int id = readInteger();

        Order order = system.findOrderById(id);

        if (order == null) {
            System.out.println(
                    "No se encontró ese pedido."
            );
            return;
        }

        boolean ready =
                cook.markOrderReady(order);

        if (ready) {
            System.out.println(
                    "El pedido fue marcado como listo."
            );
        } else {
            System.out.println(
                    "No se pudo marcar el pedido como listo."
            );
        }
    }

    // =========================================================
    // MENÚ DE INSPECTOR
    // =========================================================

    private void showInspectorMenu(
            Inspector inspector) {

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("========================");
            System.out.println(" MENÚ DE INSPECTOR");
            System.out.println("========================");
            System.out.println("1. Actualizar listado de ventas");
            System.out.println("2. Ver pedidos vendidos");
            System.out.println("3. Ver informe de ventas");
            System.out.println("0. Cerrar sesión");
            System.out.print("Elegí una opción: ");

            int option = readInteger();

            switch (option) {
                case 1:
                    inspector.receiveSalesOrders(
                            system.getCompletedOrders()
                    );

                    System.out.println(
                            "Listado de ventas actualizado."
                    );
                    break;

                case 2:
                    inspector.showSalesOrders();
                    break;

                case 3:
                    inspector.showSalesReport();
                    break;

                case 0:
                    running = false;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    // =========================================================
    // MÉTODOS GENERALES DE VISUALIZACIÓN
    // =========================================================

    private void showAllOrders() {
        if (system.getOrders().isEmpty()) {
            System.out.println(
                    "No hay pedidos registrados."
            );
            return;
        }

        showOrders(system.getOrders());
    }

    private void showOrders(
            ArrayList<Order> orders) {

        System.out.println();

        for (Order order : orders) {
            showOrderSummary(order);
        }
    }

    private void showOrder(Order order) {
        if (order == null) {
            System.out.println(
                    "No hay ningún pedido actual."
            );
            return;
        }

        System.out.println();
        System.out.println(
                "Pedido N.º " + order.getId()
        );
        System.out.println(
                "Estado: "
                + getOrderStatusText(
                        order.getStatus()
                )
        );

        if (order.getItems().isEmpty()) {
            System.out.println("No tiene ítems.");
        } else {
            System.out.println("Ítems:");

            for (int i = 0;
                    i < order.getItems().size();
                    i++) {

                Item item = order.getItems().get(i);

                System.out.println(
                        (i + 1)
                        + ". "
                        + item.getName()
                        + " - $"
                        + item.getPrice()
                );
            }
        }

        System.out.println(
                "Total: $" + order.calculateTotal()
        );
    }

    private void showOrderSummary(Order order) {
        System.out.println(
                "Pedido N.º "
                + order.getId()
                + " | Estado: "
                + getOrderStatusText(
                        order.getStatus()
                )
                + " | Ítems: "
                + order.getItems().size()
                + " | Total: $"
                + order.calculateTotal()
        );
    }

    private String getOrderStatusText(
            OrderStatus status) {

        switch (status) {
            case CREATED:
                return "Creado";

            case RECEIVED:
                return "Recibido";

            case IN_PREPARATION:
                return "En preparación";

            case READY:
                return "Listo";

            case DELIVERED:
                return "Entregado";

            case CANCELLED:
                return "Cancelado";

            default:
                return "Estado desconocido";
        }
    }

    private void showUsersBriefly() {
        if (system.getUsers().isEmpty()) {
            System.out.println(
                    "No hay usuarios registrados."
            );
            return;
        }

        System.out.println("Usuarios:");

        for (User user : system.getUsers()) {
            System.out.println(
                    user.getId()
                    + " - "
                    + user.getName()
                    + " - "
                    + getUserTypeText(user)
            );
        }
    }

    private void showEmployees() {
        System.out.println("Empleados:");

        boolean found = false;

        for (User user : system.getUsers()) {
            if (user instanceof Employee) {
                Employee employee = (Employee) user;
                found = true;

                System.out.println(
                        employee.getId()
                        + " - "
                        + employee.getName()
                        + " - "
                        + getEmployeeRoleText(
                                employee.getRole()
                        )
                );
            }
        }

        if (!found) {
            System.out.println(
                    "No hay empleados registrados."
            );
        }
    }

    private void showCooks() {
        System.out.println("Cocineros disponibles:");

        boolean found = false;

        for (User user : system.getUsers()) {
            if (user instanceof Employee) {
                Employee employee = (Employee) user;

                if (employee.isCook()) {
                    found = true;

                    System.out.println(
                            employee.getId()
                            + " - "
                            + employee.getName()
                    );
                }
            }
        }

        if (!found) {
            System.out.println(
                    "No hay cocineros asignados para hoy."
            );
        }
    }

    private ArrayList<Inspector> getInspectors() {
        ArrayList<Inspector> inspectors =
                new ArrayList<>();

        for (User user : system.getUsers()) {
            if (user instanceof Inspector) {
                inspectors.add((Inspector) user);
            }
        }

        return inspectors;
    }

    private String getUserTypeText(User user) {
        if (user instanceof Manager) {
            return "Gerente";
        }

        if (user instanceof Inspector) {
            return "Inspector";
        }

        if (user instanceof Employee) {
            Employee employee = (Employee) user;

            return "Empleado - "
                    + getEmployeeRoleText(
                            employee.getRole()
                    );
        }

        return "Usuario";
    }

    private String getEmployeeRoleText(
            EmployeeRole role) {

        switch (role) {
            case SELLER:
                return "Vendedor";

            case COOK:
                return "Cocinero";

            case UNASSIGNED:
                return "Sin rol asignado";

            default:
                return "Rol desconocido";
        }
    }

    // =========================================================
    // LECTURA DE DATOS
    // =========================================================

    private int readInteger() {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.print(
                        "Ingresá un número válido: "
                );
            }
        }
    }

    private void showInvalidOption() {
        System.out.println(
                "La opción ingresada no es válida."
        );
    }
}
