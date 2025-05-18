package database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JSONdatabase {
    private final ObjectMapper objectMapper;

    public JSONdatabase() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public <T> ArrayList<T> reader(String filePath, Class<T> valueType){
        try {
            File file = new File(filePath);
            if (file.length() == 0){
                return new ArrayList<T>();
            }
            return objectMapper.readValue(new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType));
        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Ошибка при загрузке данных из JSON");
            System.exit(0);
        }
        return null;
    }


    public boolean writer(String path, ArrayList<?> list){
        try {
            objectMapper.writeValue(new File(path), list);

        }catch (IOException ex){
            System.out.println("Ошибка при записи данных в JSON");
            return false;
        }
        return true;
    }

    public <T> boolean update(String path,T object, Class<T> valueType){
        ArrayList<T> array = reader(path,valueType );
        array.add(object);
        writer(path, array);
        return true;
    }
    public <T> boolean delete(String path, T object, Class<T> valueType){
        ArrayList<T> array = reader(path,valueType );
        for (T obj : array){
            if (obj.equals(object)){
                array.remove(object);
                writer(path, array);
                return true;
            }
        }
        return false;
    }







}
