import database.JSONdatabase;
import human.Buyer;
import human.Employee;
import human.EmployeeType;
import menu.MainUI;
import other.Company;
import other.Order;
import places.Product;
import places.Shop;
import places.Storage;


import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        String dir = "D:/Java Projects/Second Project/src/main/resources";
        File file = new File(dir);
        String[] files = file.list();
        if (files == null || files.length == 0) {
            new File(dir + "Products.json");
            new File(dir + "Storages.json");
            new File(dir + "Buyers.json");
            new File(dir + "Employees.json");
            new File(dir + "Shops.json");
            new File(dir + "Orders.json");
            new File(dir + "Money.json");
            Company.setMoney(5000);
        }
        while (true) {
            MainUI.printMainMenu();
            int choice = MainUI.readIntInput("Выберите действие: ");
            switch (choice) {
                case 1 -> MainUI.manageBuyers();
                case 2 -> MainUI.manageEmployees();
                case 3 -> MainUI.manageProducts();
                case 4 -> MainUI.manageShops();
                case 5 -> MainUI.manageStorages();
                case 6 -> MainUI.manageOrders();
                case 7 -> MainUI.showReports();
                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный ввод!");
            }
        }




    }



}
