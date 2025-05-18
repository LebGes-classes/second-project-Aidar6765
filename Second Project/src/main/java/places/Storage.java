package places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.JSONdatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Storage {

    static JSONdatabase json = new JSONdatabase();
    private int capacity;
    private String address;
    private int id;
    private ArrayList<Cell> storage;

    public int getCapacity() {
        return capacity;
    }
    public String getAddress(){
        return address;
    }

    public int getId() {
        return id;
    }

    public void setStorage(ArrayList<Cell> storage) {
        this.storage = storage;
    }

    public ArrayList<Cell> getStorage() {
        return storage;
    }

    @JsonCreator
    public Storage(@JsonProperty("id") int id,
                   @JsonProperty("cell space") int cellSpace,
                   @JsonProperty("address") String address,
                   @JsonProperty("capacity") int capacity){

        this.capacity = capacity;
        this.address = address;
        this.id = id;
        storage = new ArrayList<>();
        for (int i = 0; i < capacity; i++){
            storage.add(new Cell(cellSpace));
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage1 = (Storage) o;
        return capacity == storage1.capacity && id == storage1.id && Objects.equals(address, storage1.address) && Objects.equals(storage, storage1.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, address, id, storage);
    }

    public static boolean openNewStorage(int id, int cellSpace, String address, int capacity){
        Storage storage = new Storage(id, cellSpace, address, capacity);
        for (Storage itStorage : json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class)){
            if (itStorage.getId() == storage.getId()){
                return false;
            }
        }
        json.update("D:/Java Projects/Second Project/src/main/resources/Storages.json",
                storage, Storage.class);
        return true;
    }
    public static boolean closeStorage(int id){
        ArrayList<Storage> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json",
                Storage.class);
        for (Storage storage : array){
            if (id == storage.getId()){
                array.remove(storage);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json",
                        array);
                return true;
            }
        }
        return false;
    }

    public static boolean moveProductsOnStorage(int firstStorageId, int secondStorageId, Product product, int quintity){
        Storage firstStorage = null;
        Storage secondStorage = null;
        if (firstStorageId == secondStorageId){
            System.out.println("Указан id одного и того же склада");
            return false;
        }
        ArrayList<Storage> storages = json.reader("D:/Java Projects/Second Project/src/main/resources/Storages.json", Storage.class);
        for (Storage storage : storages){
            if (storage.getId() == firstStorageId){
                firstStorage = storage;
            }
            else if(storage.getId() == secondStorageId){
                secondStorage = storage;
            }
        }
        if (firstStorage == null){
            System.out.println("Склад с первым ID не найден");
            return false;
        }
        else if(secondStorage == null){
            System.out.println("Склад с вторым ID не найден");
            return false;
        }

        ArrayList<Cell> firstStorageArray = firstStorage.getStorage();
        ArrayList<Cell> secondStorageArray = secondStorage.getStorage();

        int itemQuantity = 0;
        boolean flagEnoughItem = false;


        for (Cell cell : firstStorageArray){
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
            System.out.println("На первом складе недостаточно товара");
            return false;
        }

        for (Cell cell : secondStorageArray){
            if (cell.getProduct() != null && cell.getProduct().getId() == product.getId()){
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
                for (Storage storage : storages){
                    if (storage.getId() == firstStorageId){
                        storage.setStorage(firstStorageArray);
                    }
                    else if(storage.getId() == secondStorageId){
                        storage.setStorage(secondStorageArray);
                    }
                }
                json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
                return true;
            }
        }

        for (Cell cell : secondStorageArray){
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
                for (Storage storage : storages){
                    if (storage.getId() == firstStorageId){
                        storage.setStorage(firstStorageArray);
                    }
                    else if(storage.getId() == secondStorageId){
                        storage.setStorage(secondStorageArray);
                    }
                }
                json.writer("D:/Java Projects/Second Project/src/main/resources/Storages.json", storages);
                return true;
            }
        }
        System.out.println("На втором складе недостаточно места");
        return false;
    }




}
