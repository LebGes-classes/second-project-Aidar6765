package menu;

import database.JSONdatabase;
import human.Buyer;
import human.Employee;
import human.EmployeeType;
import other.Company;
import places.Cell;
import places.Product;
import places.Shop;
import places.Storage;
import other.Order;

import java.util.ArrayList;
import java.util.Scanner;

public class MainUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static JSONdatabase json = new JSONdatabase();

    public static void printMainMenu() {
        System.out.println("\nГлавное меню:");
        System.out.println("1. Управление покупателями");
        System.out.println("2. Управление сотрудниками");
        System.out.println("3. Управление товарами");
        System.out.println("4. Управление магазинами");
        System.out.println("5. Управление складами");
        System.out.println("6. Управление заказами");
        System.out.println("7. Отчеты и аналитика");
        System.out.println("0. Выход");
    }

    public static void manageBuyers() {
        while (true) {
            System.out.println("\nУправление покупателями:");
            System.out.println("1. Добавить покупателя");
            System.out.println("2. Удалить покупателя");
            System.out.println("3. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> addBuyerUI();
                case 2 -> deleteBuyerUI();
                case 3 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void addBuyerUI() {
        int id = readIntInput("Введите ID покупателя: ");
        int money = readIntInput("Введите количество денег: ");
        String name = readStringInput("Введите имя: ");
        String surname = readStringInput("Введите фамилию: ");
        boolean success = Buyer.addBuyer(id, money, name, surname);
        System.out.println(success ? "Покупатель добавлен!" : "Ошибка добавления! Покупатель с таким id уже есть");
    }

    public static void deleteBuyerUI() {
        int id = readIntInput("Введите ID покупателя: ");
        boolean success = Buyer.deleteBuyer(id);
        System.out.println(success ? "Покупатель удален!" : "Покупатель не найден!");
    }

    public static void manageEmployees() {
        while (true) {
            System.out.println("\nУправление сотрудниками:");
            System.out.println("1. Нанять сотрудника");
            System.out.println("2. Уволить сотрудника");
            System.out.println("3. Активировать сотрудника");
            System.out.println("4. Деактивировать сотрудника");
            System.out.println("5. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> hireEmployeeUI();
                case 2 -> dismissEmployeeUI();
                case 3 -> activateEmployeeUI();
                case 4 -> disactivateEmployeeUI();
                case 5 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void hireEmployeeUI() {
        int id = readIntInput("Введите ID сотрудника: ");
        String name = readStringInput("Введите имя: ");
        EmployeeType type = readEmployeeType();
        int salary = readIntInput("Введите зарплату: ");
        boolean success = Employee.hireEmployee(id, name, type, salary);
        System.out.println(success ? "Сотрудник нанят!" : "Ошибка найма! Сотрудник с таким id не найден");
    }

    public static EmployeeType readEmployeeType() {
        while (true) {
            System.out.println("Типы сотрудников: 1 - MANAGER, 2 - SELLER, 3 - SUPPLIER");
            int choice = readIntInput("Выберите тип: ");
            switch (choice) {
                case 1 -> { return EmployeeType.MANAGER; }
                case 2 -> { return EmployeeType.SELLER; }
                case 3 -> { return EmployeeType.SUPPLIER; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void dismissEmployeeUI() {
        int id = readIntInput("Введите ID сотрудника: ");
        boolean success = Employee.dismissEmployee(id);
        System.out.println(success ? "Сотрудник уволен!" : "Сотрудник не найден!");
    }

    public static void activateEmployeeUI() {
        int id = readIntInput("Введите ID сотрудника: ");
        boolean success = Employee.activateEmployee(id);
        System.out.println(success ? "Сотрудник активирован!" : "Ошибка! Сотрудник с таким id не найден");
    }

    public static void disactivateEmployeeUI() {
        int id = readIntInput("Введите ID сотрудника: ");
        boolean success = Employee.disactivateEmployee(id);
        System.out.println(success ? "Сотрудник деактивирован!" : "Ошибка! Сотрудник с таким id не найден");
    }



    public static void manageProducts() {
        while (true) {
            System.out.println("\nУправление товарами:");
            System.out.println("1. Добавить товар");
            System.out.println("2. Удалить товар");
            System.out.println("3. Купить товар на склад");
            System.out.println("4. Вернуть товар");
            System.out.println("5. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> addProductUI();
                case 2 -> deleteProductUI();
                case 3 -> buyProductUI();
                case 4 -> returnProductUI();
                case 5 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void addProductUI() {
        int id = readIntInput("Введите ID товара: ");
        String name = readStringInput("Введите название: ");
        int cost = readIntInput("Введите стоимость: ");
        int space = readIntInput("Введите занимаемое место: ");
        boolean success = Product.addProduct(id, name, cost, space);
        System.out.println(success ? "Товар добавлен!" : "Ошибка! Товар с таким id уже есть");
    }

    public static void deleteProductUI() {
        int id = readIntInput("Введите ID товара: ");
        boolean success = Product.deleteProduct(id);
        System.out.println(success ? "Товар удален!" : "Ошибка! Продукта с таким не существует");
    }

    public static void buyProductUI() {
        int productId = readIntInput("Введите ID товара: ");
        int storageId = readIntInput("Введите ID склада: ");
        int quantity = readIntInput("Введите количество: ");
        boolean success = Product.buyProducts(productId, storageId, quantity);
        System.out.println(success ? "Товар куплен!" : "Ошибка! Товара или склада с таким id не существует");
    }

    public static void returnProductUI() {
        int storageId = readIntInput("Введите ID склада: ");
        int productId = readIntInput("Введите ID товара: ");
        int quantity = readIntInput("Введите количество: ");
        boolean success = Product.returnProduct(storageId, productId, quantity);
        System.out.println(success ? "Товар возвращен!" : "Ошибка! Товара или склада с таким id не существует");
    }


    public static void manageShops() {
        while (true) {
            System.out.println("\nУправление магазинами:");
            System.out.println("1. Открыть новый магазин");
            System.out.println("2. Закрыть магазин");
            System.out.println("3. Назначить ответственного");
            System.out.println("4. Продать товар из заказа");
            System.out.println("5. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> openShopUI();
                case 2 -> closeShopUI();
                case 3 -> changeResponsibleUI();
                case 4 -> sellProductUI();
                case 5 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void openShopUI() {
        int id = readIntInput("Введите ID магазина: ");
        String address = readStringInput("Введите адрес: ");
        boolean success = Shop.openNewShop(id, address);
        System.out.println(success ? "Магазин открыт!" : "Ошибка! Магазин с таким id уже есть");
    }

    public static void closeShopUI() {
        int id = readIntInput("Введите ID магазина: ");
        boolean success = Shop.closeNewShop(id);
        System.out.println(success ? "Магазин закрыт!" : "Ошибка! Магазин с таким id  не существует");
    }

    public static void changeResponsibleUI() {
        int shopId = readIntInput("Введите ID магазина: ");
        int personId = readIntInput("Введите ID сотрудника: ");
        boolean success = Shop.changeResponsiblePerson(shopId, personId);
        System.out.println(success ? "Ответственный изменен!" : "Ошибка!");
    }

    public static void sellProductUI() {
        int orderId = readIntInput("Введите ID заказа: ");
        int storageId = readIntInput("Введите ID склада: ");
        boolean success = Shop.sellProduct(orderId, storageId);
        System.out.println(success ? "Товар продан!" : "Ошибка! Склада или заказа с данным id не существует ");
    }



    public static void manageStorages() {
        while (true) {
            System.out.println("\nУправление складами:");
            System.out.println("1. Открыть новый склад");
            System.out.println("2. Закрыть склад");
            System.out.println("3. Переместить товары");
            System.out.println("4. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> openStorageUI();
                case 2 -> closeStorageUI();
                case 3 -> moveProductsUI();
                case 4 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void openStorageUI() {
        int id = readIntInput("Введите ID склада: ");
        int cellSpace = readIntInput("Введите место в ячейке: ");
        String address = readStringInput("Введите адрес: ");
        int capacity = readIntInput("Введите вместимость: ");
        boolean success = Storage.openNewStorage(id, cellSpace, address, capacity);
        System.out.println(success ? "Склад открыт!" : "Ошибка! Склад с таким id уже существует");
    }

    public static void closeStorageUI() {
        int id = readIntInput("Введите ID склада: ");
        boolean success = Storage.closeStorage(id);
        System.out.println(success ? "Склад закрыт!" : "Ошибка! Склада с таким id нет");
    }

    public static void moveProductsUI() {
        int from = readIntInput("Введите ID исходного склада: ");
        int to = readIntInput("Введите ID целевого склада: ");
        int productId = readIntInput("Введите ID товара: ");
        int quantity = readIntInput("Введите количество: ");
        Product product = findProduct(productId);
        if (product == null) {
            System.out.println("Товар не найден!");
            return;
        }
        boolean success = Storage.moveProductsOnStorage(from, to, product, quantity);
        System.out.println(success ? "Товары перемещены!" : "Ошибка!");
    }



    public static void manageOrders() {
        while (true) {
            System.out.println("\nУправление заказами:");
            System.out.println("1. Создать заказ");
            System.out.println("2. Назад");
            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> createOrderUI();
                case 2 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    public static void createOrderUI() {
        int buyerId = readIntInput("Введите ID покупателя: ");
        int productId = readIntInput("Введите ID товара: ");
        int shopId = readIntInput("Введите ID магазина: ");
        int quantity = readIntInput("Введите количество: ");
        boolean success = Buyer.makeOrder(buyerId, productId, shopId, quantity);
        System.out.println(success ? "Заказ создан!" : "Ошибка!");
    }



    public static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    public static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static Product findProduct(int productId) {

        return new Product(productId, "Dummy", 0, 0); // Заглушка
    }
    public static void showReports() {
        System.out.println("\nОтчеты и аналитика:");
        System.out.println("1. Информация об объектах");
        System.out.println("2. Просмотр списков ID");
        System.out.println("3. Назад");

        int choice = readIntInput("Выберите действие: ");
        switch (choice) {
            case 1 -> showObjectsInfo();
            case 2 -> showIdLists();
            case 3 -> { return; }
            default -> System.out.println("Неверный ввод!");
        }
    }

    private static void showObjectsInfo() {
        while (true) {
            System.out.println("\nИнформация об объектах:");
            System.out.println("1. О складе");
            System.out.println("2. О пункте продаж");
            System.out.println("3. Товары на складе");
            System.out.println("4. Товары в пункте продаж");
            System.out.println("5. Баланс компании");
            System.out.println("6. Назад");

            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> showStorageInfo();
                case 2 -> showShopInfo();
                case 3 -> showStorageProducts();
                case 4 -> showShopProducts();
                case 5 -> showCompanyBalance();
                case 6 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    private static void showIdLists() {
        while (true) {
            System.out.println("\nПросмотр списков ID:");
            System.out.println("1. Склады");
            System.out.println("2. Пункты продаж");
            System.out.println("3. Сотрудники");
            System.out.println("4. Покупатели");
            System.out.println("5. Заказы");
            System.out.println("6. Назад");

            int choice = readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> displayStoragesList();
                case 2 -> displayShopsList();
                case 3 -> displayEmployeesList();
                case 4 -> displayBuyersList();
                case 5 -> displayOrdersList();
                case 6 -> { return; }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    private static void displayOrdersList() {
        ArrayList<Order> orders = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class);
        System.out.println("\nСписок заказов:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("ID заказа | Магазин | Покупатель | Товар | Количество | Сумма");
        System.out.println("-------------------------------------------------------------");

        for (Order order : orders) {
            System.out.printf("%-9d | %-7d | %-10s | %-5d | %-10d | %-7d%n",
                    order.getId(),
                    order.getShopId(),
                    order.getBuyer().getSurname(),
                    order.getProduct().getId(),
                    order.getQuantity(),
                    order.getCost());
        }
    }
    private static void displayStoragesList() {
        ArrayList<Storage> storages = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class);
        System.out.println("\nСписок складов:");
        storages.forEach(s -> System.out.printf(
                "ID: %d | Адрес: %s ",
                s.getId(),
                s.getAddress()
        ));
    }

    private static void displayShopsList() {
        ArrayList<Shop> shops = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        System.out.println("\nСписок пунктов продаж:");
        shops.forEach(s -> System.out.printf(
                "ID: %d | Адрес: %s | Статус: %s%n",
                s.getId(),
                s.getAddress(),
                s.getIsOpen() ? "Открыт" : "Закрыт"
        ));
    }

    private static void displayEmployeesList() {
        ArrayList<Employee> employees = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        System.out.println("\nСписок сотрудников:");
        employees.forEach(e -> System.out.printf(
                "ID: %d | Имя: %s | Должность: %s ",
                e.getId(),
                e.getName(),
                e.getType()
        ));
    }

    private static void displayBuyersList() {
        ArrayList<Buyer> buyers = json.reader("D:/Java Projects/Second Project/src/main/resources/Buyers.json", Buyer.class);
        System.out.println("\nСписок покупателей:");
        buyers.forEach(b -> System.out.printf(
                "ID: %d | Имя: %s %s | Баланс: %d%n",
                b.getId(),
                b.getName(),
                b.getSurname(),
                b.getMoney()
        ));
    }




    public static void showStorageInfo() {
        int storageId = readIntInput("Введите ID склада: ");
        Storage storage = findStorage(storageId);
        if (storage != null) {
            System.out.println("\nИнформация о складе:");
            System.out.println("ID: " + storage.getId());
            System.out.println("Адрес: " + storage.getAddress());
            System.out.println("Вместимость: " + storage.getCapacity());
            System.out.println("Занятое место: " + calculateOccupiedSpace(storage));
        } else {
            System.out.println("Склад не найден!");
        }
    }

    public static void showShopInfo() {
        int shopId = readIntInput("Введите ID магазина: ");
        Shop shop = findShop(shopId);
        if (shop != null) {
            System.out.println("\nИнформация о магазине:");
            System.out.println("ID: " + shop.getId());
            System.out.println("Адрес: " + shop.getAddress());
            System.out.println("Статус: " + (shop.getIsOpen() ? "Открыт" : "Закрыт"));
            System.out.println("Ответственный: " +
                    (shop.getResponsiblePerson() != null ?
                            shop.getResponsiblePerson().getName() : "Не назначен"));
        } else {
            System.out.println("Магазин не найден!");
        }
    }

    public static void showStorageProducts() {
        int storageId = readIntInput("Введите ID склада: ");
        Storage storage = findStorage(storageId);
        if (storage == null) {
            System.out.println("Склад не найден!");
            return;
        }

        System.out.println("\nТовары на складе " + storageId + ":");
        for (Cell cell : storage.getStorage()) {
            if (cell.getProduct() != null) {
                Product p = cell.getProduct();
                int quantity = cell.getOccupiedSpace() / p.getSpace();
                System.out.printf("%s (ID: %d) - %d шт.%n",
                        p.getName(), p.getId(), quantity);
            }
        }
    }

    public static void showShopProducts() {
        int shopId = readIntInput("Введите ID магазина: ");

        ArrayList<Order> orders = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class);
        System.out.println("\nТовары в пункте продаж " + shopId + ":");
        orders.stream()
                .filter(o -> o.getShopId() == shopId)
                .forEach(order -> System.out.printf("%s (ID: %d) - %d шт.%n",
                        order.getProduct().getName(),
                        order.getProduct().getId(),
                        order.getQuantity()));
    }

    public static void showAvailableProducts() {
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        System.out.println("\nДоступные для закупки товары:");
        products.forEach(p -> System.out.printf("%s (ID: %d) - Цена: %d%n",
                p.getName(), p.getId(), p.getCost()));
    }

    public static void showCompanyProfit() {
        int totalIncome = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class).stream()
                .mapToInt(Order::getCost).sum();
        int expenses = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class).stream()
                .mapToInt(Employee::getSalary).sum();

        System.out.println("\nДоходность предприятия:");
        System.out.println("Общий доход: " + totalIncome);
        System.out.println("Расходы: " + expenses);
        System.out.println("Чистая прибыль: " + (totalIncome - expenses));
    }

    public static void showShopProfit() {
        int shopId = readIntInput("Введите ID магазина: ");
        int shopIncome = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class).stream()
                .filter(o -> o.getShopId() == shopId)
                .mapToInt(Order::getCost).sum();

        System.out.println("\nДоходность пункта продаж " + shopId + ":");
        System.out.println("Общий доход: " + shopIncome);
    }

    private static void showCompanyBalance() {
        int balance = Company.getMoney();
        System.out.println("\nТекущий баланс компании:");
        System.out.println("-----------------------");
        System.out.printf("| %-20s | %10d |%n", "Сумма на счету", balance);
        System.out.println("-----------------------");
    }

    public static Storage findStorage(int id) {
        return json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class).stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Shop findShop(int id) {
        return json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class).stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static int calculateOccupiedSpace(Storage storage) {
        return storage.getStorage().stream()
                .mapToInt(Cell::getOccupiedSpace)
                .sum();
    }
}
