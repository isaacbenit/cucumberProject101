package Parent.Utils;

import java.util.Properties;

public class configloader {
    private final Properties properties;
    private static configloader configureloader;

    private configloader() throws IllegalAccessException {
        properties=PropertyUtils.propertyLoader("src/test/resources/config.properties");

    }
    public static configloader getInstance() throws IllegalAccessException {
        if(configureloader==null){
            configureloader=new configloader();
        }
        return configureloader;
    }
    public String getBaseUrl(){
        String prop=properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("property baseUrl is not specified in the stg-config.properties file");
    }

}
