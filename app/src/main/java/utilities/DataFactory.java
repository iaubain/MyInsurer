package utilities;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Hp on 11/12/2016.
 */
public class DataFactory {
    private ObjectMapper mapper;

    public DataFactory() {
    }

    public String jsonString(Object object){
        mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String jsonData=mapper.writeValueAsString(object);
            return jsonData;
        } catch (Exception e) {
            System.out.println("Exit with Error: "+e.getMessage());
        }
        return null;
    }

    public Object jsonObject(Class className, String jsonString){
        Class mClass=className;
        mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Object result=mapper.readValue(jsonString,mClass);
            return result;
        } catch (Exception e) {
            System.out.println("Exit with Error: "+e.getMessage());
        }
        return null;
    }


}
