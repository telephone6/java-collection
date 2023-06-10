package org.example.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReflectUtil {

    public static <T> List<T> transfer2Object(List<Map<String, Object>> objMapList, Class<T> tClass) throws Exception {
        List<T> list = new ArrayList<>();
        for (Map<String, Object> objMap : objMapList) {
            T t = tClass.newInstance();
            Field[] declaredFields = tClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Object o = objMap.get(declaredField.getName());
                declaredField.setAccessible(true);
                if(Objects.nonNull(o)) {
                    declaredField.set(t, String.valueOf(o));
                }
            }
            list.add(t);
        }
        return list;
    }

}
