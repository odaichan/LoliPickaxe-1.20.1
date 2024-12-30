package net.daichang.loli_pickaxe.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class FieldTrace {

    public static void traceAllFields(Object object) {
        List<String> accessLog = new ArrayList<>();
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                final Object originalValue = field.get(object);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                int originalModifiers = field.getModifiers();

                Object proxy = Proxy.newProxyInstance(
                        clazz.getClassLoader(),
                        new Class[]{field.getType()},
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxyObj, Method method, Object[] args) throws Throwable {
                                if (method.getName().equals("equals")) {
                                    return originalValue.equals(args[0]);
                                } else if (method.getName().equals("hashCode")) {
                                    return originalValue.hashCode();
                                } else if (method.getName().equals("toString")) {
                                    return originalValue.toString();
                                }
                                accessLog.add("Field: " + field.getName() + ", Method: " + method.getName() + " was called");
                                return method.invoke(originalValue, args);
                            }
                        });

                field.set(object, proxy);

                modifiersField.setInt(field, originalModifiers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
