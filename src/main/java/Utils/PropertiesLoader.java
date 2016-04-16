package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private Properties properties = new Properties();

    public PropertiesLoader() {
        try {
            this.properties.load(new FileInputStream("testrun.properties"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}

