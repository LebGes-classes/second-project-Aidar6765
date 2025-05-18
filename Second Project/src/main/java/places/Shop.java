package places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.JSONdatabase;
import human.Employee;
import human.EmployeeType;
import other.Company;
import other.Order;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Objects;

public class Shop {

    static JSONdatabase json = new JSONdatabase();
    private int id;
    private Employee responsiblePerson;
    private String address;
    private boolean isOpen;

    @JsonCreator
    public Shop(@JsonProperty("id") int id, @JsonProperty("address") String address){
        this.id = id;
        this.address = address;
        responsiblePerson = null;
        this.isOpen = false;
    }


    // Геттер для поля isOpen
    public boolean getIsOpen(){
        return isOpen;
    }
    public String getAddress(){
        return address;
    }
    public int getId(){
        return id;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Employee getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Employee responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return id == shop.id && isOpen == shop.isOpen && Objects.equals(responsiblePerson, shop.responsiblePerson) && Objects.equals(address, shop.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, responsiblePerson, address, isOpen);
    }

    // Открытие пункта продаж
    public static boolean openNewShop(int id, String address){
        Shop shop = new Shop(id , address);
        ArrayList<Shop> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);

        for (Shop itShop : array){
            if (id == itShop.getId()){
                return false;
            }
        }
        json.update("D:/Java Projects/Second Project/src/main/resources/Shops.json", shop, Shop.class);
        return true;
    }

    // Закрытие пункта продаж
    public static boolean closeNewShop(int id){
        ArrayList<Shop> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        for (Shop itShop : array){
            if (id == itShop.getId()){
                array.remove(itShop);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Shops.json", array);
                return true;
            }
        }
        return false;
    }

    public static boolean openShop(int id){
        ArrayList<Shop> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        for (Shop itShop: array){
            if (id == itShop.getId()){
                if (itShop.getResponsiblePerson() == null){
                    System.out.println("У пункта продаж нет ответственного лица. Нельзя открыть");
                    return false;
                }
                itShop.setOpen(true);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Shops.json", array);
                return true;
            }
        }
        return false;
    }

    public static boolean closeShop(int id){
        ArrayList<Shop> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        for (Shop itShop: array){
            if (id == itShop.getId()){
                itShop.setOpen(false);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Shops.json", array);
                return true;
            }
        }
        return false;
    }

    public static boolean changeResponsiblePerson(int shopId, int personId){
        ArrayList<Shop> shops = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        ArrayList<Employee> employees = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        Shop needShop = null;
        for (Shop shop : shops){
            if (shopId == shop.getId()){
                needShop = shop;
            }
        }
        if (needShop == null){
            System.out.println("Магазин с таким id не найден");
            return false;
        }

        for (Employee employee : employees){
            if (personId == employee.getId()){
                if (employee.getType() == EmployeeType.MANAGER){
                    needShop.setResponsiblePerson(employee);
                    json.writer("D:/Java Projects/Second Project/src/main/resources/Shops.json", shops);
                    return true;
                }
                else{
                    System.out.println("Данный сотрудник не является менеджером");
                    return false;
                }
            }
        }
        System.out.println("Сотрудник с таким id не найден");
        return false;
    }

    public static boolean sellProduct(int orderId, int storageId){
        ArrayList<Order> orders = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class);
        ArrayList<Storage> storages = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class);
        ArrayList<Shop> shops = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        Order order = null;
        Storage storage = null;
        Shop shop = null;


        for (Storage itStorage : storages){
            if (storageId == itStorage.getId()){
                storage = itStorage;
            }
        }
        if (storage == null){
            System.out.println("Склад с таким id не найден");
            return false;
        }

        for (Order itOrder : orders){
            if (orderId == itOrder.getId()){
                order = itOrder;
            }
        }
        if (order == null){
            System.out.println("Заказ с таким id не найден");
            return false;
        }

        for (Shop itShop : shops){
            if (order.getShopId() == itShop.getId()){
                shop = itShop;
            }
        }
        if (shop.getIsOpen() == false){
            System.out.println("В данные момент пункт выдачи с данным из заказа id закрыт");
            return false;
        }
        int quintity = order.getQuantity();
        Product product = order.getProduct();
        ArrayList<Cell> storageArray = storage.getStorage();

        int itemQuantity = 0;
        boolean flagEnoughItem = false;


        for (Cell cell : storageArray){
            if (cell.getProduct() != null && cell.getProduct().getId() == product.getId()) {
                if ((quintity - itemQuantity) > (cell.getOccupiedSpace() / product.getSpace())) {
                    itemQuantity += cell.getOccupiedSpace() / product.getSpace();
                    cell.setOccupiedSpace(0);
                    cell.setProduct(null);
                } else {
                    cell.setOccupiedSpace(cell.getOccupiedSpace() - ((quintity - itemQuantity) * product.getSpace()));
                    itemQuantity += (quintity - itemQuantity);
                    flagEnoughItem = true;
                    break;
                }

            }
        }
        if (flagEnoughItem == false){
            System.out.println("На складе недостаточно товара");
            return false;
        }

        for (Storage itStorage : storages){
            if (itStorage.getId() == storageId){
                itStorage.setStorage(storageArray);
            }
        }
        json.delete("D:/Java Projects/Second Project/src/main/resources/Orders.json", order, Order.class);
        Company.setMoney(Company.getMoney() + order.getCost());
        json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
        return true;


    }


}
