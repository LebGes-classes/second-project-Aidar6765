package places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.JSONdatabase;
import other.Company;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;

public class Product {

    static JSONdatabase json = new JSONdatabase();
    private String name;
    private int id;
    private int cost;
    private int space;

    @JsonCreator
    public Product(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("cost") int cost,
                   @JsonProperty("space") int space) {
        this.name = name;
        this.id = id;
        this.cost = cost;
        this.space = space;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public int getSpace() {
        return space;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && cost == product.cost && space == product.space && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, cost, space);
    }

    public static boolean addProduct(int id, String name, int cost, int space){
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        for (Product product : products){
            if (product.getId() == id){
                System.out.println("Продукт с таким id существует");
                return false;
            }
        }
        Product product = new Product(id, name, cost, space);
        json.update("D:/Java Projects/Second Project/src/main/resources/Products.json", product, Product.class);
        return true;
    }

    public static boolean deleteProduct(int id){
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        for (Product product : products){
            if (product.getId() == id){
                products.remove(product);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Products.json", products);
                return true;

            }
        }
        System.out.println("Продукт с таким id не найден");
        return false;
    }

    public static boolean buyProducts(int productId, int storageId, int quantity){
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        ArrayList<Storage> storages = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class);

        Product product = null;
        Storage storage = null;

        for (Product itProduct : products){
            if (itProduct.getId() == productId){
                product = itProduct;
            }
        }
        if (product == null){
            System.out.println("Продукт с таким id не найден");
            return false;
        }

        for (Storage itStorage : storages){
            if (storageId == itStorage.getId()){
                storage = itStorage;
            }
        }
        if (storage == null){
            System.out.println("Склад с таким id не найден");
            return false;
        }

        int cost = quantity * product.getCost();
        if (cost > Company.getMoney()){
            System.out.println("У вас недостаточно средств");
            return false;
        }

        int itemQuantity = quantity;

        ArrayList<Cell> storageArray = storage.getStorage();

        for (Cell cell : storageArray){
            if (cell.getProduct() != null && cell.getProduct().getId() == productId){
                if (itemQuantity -((cell.getCellSpace() - cell.getOccupiedSpace()) / product.getSpace()) > 0){
                    int prevItemQuantity = itemQuantity;
                    itemQuantity -= ((cell.getCellSpace() - cell.getOccupiedSpace()) / product.getSpace());
                    cell.setOccupiedSpace(cell.getOccupiedSpace() + (prevItemQuantity - itemQuantity) * product.getSpace());

                }
                else{
                    cell.setOccupiedSpace(cell.getOccupiedSpace() + itemQuantity * product.getSpace());
                    itemQuantity = 0;
                }

            }
            if (itemQuantity == 0){
                for (Storage itStorage : storages){
                    if (itStorage.getId() == storageId){
                        itStorage.setStorage(storageArray);
                    }
                }
                Company.setMoney(Company.getMoney() - cost);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
                return true;
            }
        }

        for (Cell cell : storageArray){
            if (cell.getProduct() == null){
                if (itemQuantity -(cell.getCellSpace() / product.getSpace()) > 0){
                    cell.setProduct(product);
                    int prevItemQuantity = itemQuantity;
                    itemQuantity -= (cell.getCellSpace() / product.getSpace());
                    cell.setOccupiedSpace((prevItemQuantity - itemQuantity) * product.getSpace());

                }
                else{
                    cell.setProduct(product);
                    cell.setOccupiedSpace(itemQuantity * product.getSpace());
                    itemQuantity = 0;
                }

            }
            if (itemQuantity == 0){
                for (Storage itStorage : storages){

                    if(storage.getId() == storageId){
                        itStorage.setStorage(storageArray);
                    }
                }
                Company.setMoney(Company.getMoney() - cost);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
                return true;
            }
        }
        System.out.println("На складе недостаточно места");
        return false;
    }
    
    public static boolean returnProduct(int storageId, int productId, int quantity){
        int itemQuantity = 0;
        ArrayList<Product> products = json.reader("D:/Java Projects/Second Project/src/main/resources/Products.json", Product.class);
        ArrayList<Storage> storages = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class);

        Product product = null;
        Storage storage = null;

        for (Product itProduct : products){
            if (itProduct.getId() == productId){
                product = itProduct;
            }
        }
        if (product == null){
            System.out.println("Продукт с таким id не найден");
            return false;
        }

        for (Storage itStorage : storages){
            if (storageId == itStorage.getId()){
                storage = itStorage;
            }
        }
        if (storage == null){
            System.out.println("Склад с таким id не найден");
            return false;
        }
        
        ArrayList<Cell> storageArray = storage.getStorage();
        boolean flagEnoughItem = false;

        for (Cell cell : storageArray){
            if (cell.getProduct() != null && cell.getProduct().getId() == product.getId()) {
                if ((quantity - itemQuantity) > (cell.getOccupiedSpace() / product.getSpace())) {
                    itemQuantity += cell.getOccupiedSpace() / product.getSpace();
                    cell.setOccupiedSpace(0);
                    cell.setProduct(null);
                } else {
                    cell.setOccupiedSpace(cell.getOccupiedSpace() - ((quantity - itemQuantity) * product.getSpace()));
                    itemQuantity += (quantity - itemQuantity);
                    flagEnoughItem = true;
                    break;
                }

            }

        }
        if (flagEnoughItem == false){
            System.out.println("На складе нет такого количества данного товара");
            return false;
        }
        else{
            for (Storage itStorage : storages){
                if (itStorage.getId() == storageId){
                    itStorage.setStorage(storageArray);
                }
            }
            Company.setMoney(Company.getMoney() + (product.getCost() * quantity));
            json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
            return true;
        }

    }


}
