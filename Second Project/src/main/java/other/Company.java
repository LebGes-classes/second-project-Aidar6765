package other;

import database.JSONdatabase;

import java.util.ArrayList;

public class Company {
    static JSONdatabase json = new JSONdatabase();

    public static int getMoney(){
        return json.reader("D:/Java Projects/Second Project/src/main/resources/Money.json", Integer.class).get(0);
    }
    public static void setMoney(int money){
        ArrayList<Integer> array = new ArrayList<>();
        array.add(money);
        json.writer("D:/Java Projects/Second Project/src/main/resources/Money.json", array);
    }
}
