import org.junit.jupiter.api.*;
import places.Product;
import places.Shop;
import places.Storage;
import human.Buyer;
import human.Employee;
import human.EmployeeType;
import other.Company;
import other.Order;
import database.JSONdatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static final String TEST_DIR = "src/main/resources/";
    private static JSONdatabase testJson = new JSONdatabase();

    @BeforeEach
    void loadFiles() throws IOException {
        new File("Products.json");
        new File("Storages.json");
        new File("Buyers.json");
        new File("Employees.json");
        new File("Shops.json");
        new File("Orders.json");
        new File("Money.json");
        Company.setMoney(5000);

    }

    private void copyFile(String filename) throws IOException {
        File source = new File("src/main/resources/" + filename);
        File dest = new File(TEST_DIR + filename);
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }


    @Test
    void testCompanyMoneyFlow() {
        Company.setMoney(1000);
        assertEquals(1000, Company.getMoney());
        Company.setMoney(500);
        assertEquals(500, Company.getMoney());
    }
    @Test
    void testProductManagement() {
        assertTrue(Product.addProduct(999, "Test Product", 100, 2));
        assertFalse(Product.addProduct(999, "Duplicate", 200, 3));
        assertTrue(Product.deleteProduct(999));
    }
    @Test
    void testShopOperations() {

        assertTrue(Shop.openNewShop(999, "Test Address"));
        Employee.hireEmployee(999, "Manager", EmployeeType.MANAGER, 500);
        assertTrue(Shop.changeResponsiblePerson(999, 999));
        assertTrue(Shop.openShop(999));
    }
    @Test
    void testStorageOperations() {

        assertTrue(Storage.openNewStorage(999, 10, "Storage Address", 100));
        Storage.openNewStorage(888, 100, "sdfsd", 400);

        Product.addProduct(888, "Test Item", 50, 1);
        Product.buyProducts(888, 999, 10);
        assertTrue(Storage.moveProductsOnStorage(999, 888, new Product(888, "Test Item", 50, 1), 10));
    }
    @Test
    void testBuyerOrder() {
        Buyer.addBuyer(999, 1000, "John", "Doe");
        Product.addProduct(777, "Test", 100, 1);
        Shop.openNewShop(888, "Shop Address");

        assertTrue(Buyer.makeOrder(999, 777, 888, 2));
    }
    @Test
    void testEmployeeActivation() {
        Employee.hireEmployee(777, "Test", EmployeeType.SELLER, 300);
        assertTrue(Employee.activateEmployee(777));
        assertTrue(Employee.disactivateEmployee(777));
    }
    @AfterEach
    void restoreFiles() throws IOException {
        new File(TEST_DIR + "Products.json").delete();
        new File(TEST_DIR + "Storages.json").delete();
        new File(TEST_DIR + "Buyers.json").delete();
        new File(TEST_DIR + "Employees.json").delete();
        new File(TEST_DIR + "Shops.json").delete();
        new File(TEST_DIR + "Orders.json").delete();
        new File(TEST_DIR + "Money.json").delete();
    }
}
