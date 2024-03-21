package test.ui.properties;

import java.io.IOException;
import java.util.Properties;

public class PropsHelper {
    private static final Properties properties = new Properties();
    private static PropsHelper instance;
    PropsHelper(){
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("local.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static PropsHelper getInstance(){
        if (instance == null){
            instance = new PropsHelper();
        }
        return instance;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
