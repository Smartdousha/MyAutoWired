package me.dousha.myautowired.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 类容器
 */
public class ClassContainer {
    private final Map<Class<?>, Object> classes = new HashMap<>();

    public ClassContainer() {
        List<Class<?>> scanClasses = new ArrayList<>();
        scanClasses.add(TestClass.class);
        scanClasses.add(TestClass2.class);
        scanClasses.add(TestClass3.class);

        scanClasses.forEach(
                it -> {
                    try {
                        classes.put(it, it.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
        );
        autoWired();
    }

    public void autoWired() {
        for (Map.Entry<Class<?>, Object> classObjectEntry : classes.entrySet()) {
            for (Field declaredField : classObjectEntry.getKey().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object obj = classes.get(declaredField.getType());
                if (obj != null) {
                    Object value = classObjectEntry.getValue();
                    try {
                        declaredField.set(value, obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public <T> T create(Class<T> klass, Object instance) {
        classes.put(klass, instance);
        return (T) classes.get(klass);
    }

    public <T> T create(Class<T> klass) {
        if (classes.get(klass) == null) {
            try {
                classes.put(klass, klass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return (T) classes.get(klass);
    }

    public Map<Class<?>, Object> getClasses() {
        return classes;
    }
}
