package human;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import database.JSONdatabase;
import places.Shop;

import java.util.ArrayList;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    static JSONdatabase json = new JSONdatabase();
    private int id;
    private String name;
    private boolean isActive;
    private EmployeeType type;
    private int salary;


    @JsonCreator
    public Employee(@JsonProperty("id") int id,
                    @JsonProperty("name") String name,
                    @JsonProperty("type") EmployeeType type,
                    @JsonProperty("salary") int salary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isActive = false;
        this.salary = salary;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }


    public EmployeeType getType() {
        return type;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public int getSalary() {
        return salary;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && isActive == employee.isActive && salary == employee.salary && Objects.equals(name, employee.name) && type == employee.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, type, salary);
    }

    public static boolean hireEmployee(int id, String name, EmployeeType type, int salary){
        Employee employee = new Employee(id, name, type, salary);
        ArrayList<Employee> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        for (Employee itEmployee : array){
            if (itEmployee.getId() == employee.getId()){
                return false;
            }
        }
        json.update("D:/Java Projects/Second Project/src/main/resources/Employees.json", employee, Employee.class);
        return true;
    }

    public static boolean dismissEmployee(int id){
        ArrayList<Employee> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        for (Employee employee : array){
            if (id == employee.getId()){
                array.remove(employee);
                ArrayList<Shop> shops = json.reader("D:/Java Projects/Second Project/src/main/resources/Shops.json", Shop.class);
                for (Shop itShop : shops){
                    if (itShop.getResponsiblePerson().getId() == id){
                        itShop.setResponsiblePerson(null);
                        itShop.setOpen(false);
                    }
                }
                json.writer("D:/Java Projects/Second Project/src/main/resources/Shops.json", shops);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Employees.json", array);
                return true;
            }
        }
        return false;
    }

    public static boolean activateEmployee(int id){
        ArrayList<Employee> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        for (Employee employee : array){
            if (id == employee.getId()){
                employee.setActive(true);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Employees.json", array);
                return true;
            }
        }
        return false;
    }

    public static boolean disactivateEmployee(int id){
        ArrayList<Employee> array = json.reader("D:/Java Projects/Second Project/src/main/resources/Employees.json", Employee.class);
        for (Employee employee : array){
            if (id == employee.getId()){
                employee.setActive(false);
                json.writer("D:/Java Projects/Second Project/src/main/resources/Employees.json", array);
                return true;
            }
        }
        return false;
    }



}
