package human;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.JSONdatabase;
import other.Order;
import places.Product;
import places.Shop;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Buyer {

    static JSONdatabase json = new JSONdatabase();
    private int money;
    private String name;
    private String surname;
    private int id;

    @JsonCreator
    public Buyer(@JsonProperty("id") int id,
                 @JsonProperty("money") int money,
                 @JsonProperty("name") String name,
                 @JsonProperty("surname") String surname) {
        this.money = money;
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return money == buyer.money && id == buyer.id && Objects.equals(name, buyer.name) && Objects.equals(surname, buyer.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, name, surname, id);
    }

    public static boolean addBuyer(int id, int money, String name, String surname){
        ArrayList<Buyer> buyers = json.reader("D:/Java Projects/Second Project/src/main/resources/Buyers.json", Buyer.class);
        for (Buyer buyer : buyers){
            if (buyer.getId() == id){
                return false;
            }
        }
        Buyer buyer = new Buyer(id, money, name, surname);
        json.update("D:/Java Projects/Second Project/src/main/resources/Buyers.json", buyer, Buyer.class);
        return true;
    }

    public static boolean deleteBuyer(int id){
        ArrayList<Buyer> buyers = json.reader("D:/Java Projects/Second Project/src/main/resources/Buyers.json", Buyer.class);
        for (Buyer buyer : buyers){
            if (id == buyer.getId()){
                buyers.remove(buyer);
                ArrayList<Order> orders = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class);
                for (Order itOrder : orders){
                    if (itOrder.getBuyer() == buyer){
                        json.delete("D:/Java Projects/Second Project/src/main/resources/Orders.json", itOrder, Order.class);
                    }
                }
                json.writer("D:/Java Projects/Second Project/src/main/resources/Buyers.json", buyers);
                return true;
            }
        }
        return false;
    }

    public static boolean makeOrder(int buyerId, int productId, int shopId,  int quantity){
        ArrayList<Buyer> buyers = json.reader("D:/Java Projects/Second Project/src/main/resources/Buyers.json", Buyer.class);
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        ArrayList<Order> orders = json.reader("D:/Java Projects/Second Project/src/main/resources/Orders.json", Order.class);
        ArrayList<Shop> shops = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
        Product product = null;
        Buyer buyer = null;
        Shop shop = null;
        for (Product itProduct : products){
            if (itProduct.getId() == productId){
                product = itProduct;
                break;
            }
        }
        if (product == null){
            System.out.println("Продукт с таким id не найден");
            return false;
        }

        for (Buyer itBuyer : buyers){
            if (itBuyer.getId() == buyerId){
                buyer = itBuyer;
                break;
            }
        }
        if (buyer == null){
            System.out.println("Покупать с таким id не найден");
            return false;
        }
        int cost = product.getCost() * quantity;

        if (cost > buyer.getMoney()){
            System.out.println("У покупателя недостаточно средств");
            return false;
        }

        for (Shop itShop : shops){
            if (itShop.getId() == shopId){
                shop = itShop;
            }
        }
        if (shop == null){
            System.out.println("Пункт выдачи с таким id не найден");
        }

        buyer.setMoney(buyer.getMoney() - cost);
        json.writer("D:/Java Projects/Second Project/src/main/resources/Buyers.json", buyers);

        boolean flag = false;
        Random rnd = new Random();
        int orderId = 0;
        while (flag == false) {
            orderId = rnd.nextInt(1, 10000000);
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    flag = true;
                    break;
                }
            }
            if (flag == true){
                flag = false;
                continue;
            }
            flag = true;



        }
        Order order = new Order(orderId, shopId, buyer, product, quantity, cost);
        json.update("D:/Java Projects/Second Project/src/main/resources/Orders.json", order, Order.class);
        return true;

    }


}
