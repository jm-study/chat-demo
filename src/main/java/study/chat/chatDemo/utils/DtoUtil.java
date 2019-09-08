package study.chat.chatDemo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class DtoUtil {
    public static Object convertMapToObject(Map map, Object objClass) {
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        while(itr.hasNext()) {
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Method[] methods = objClass.getClass().getDeclaredMethods();
                for(int i = 0; i<=methods.length-1; i++){
                    if(methodString.equals(methods[i].getName())){
                        System.out.println("invoke : "+methodString);
                        methods[i].invoke(objClass, map.get(keyAttribute));
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } return objClass;


    }

    public static Map<String, Object> jsonStringToMap(String json) {
        Map<String, Object> resultMap = null;
        try {
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            log.debug("resultMap=>" + resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Map ConverObjectToMap(Object obj) {
        try {
            //Field[] fields = obj.getClass().getFields();
            // private field는 나오지 않음.
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for (int i = 0; i <= fields.length - 1; i++) {
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

