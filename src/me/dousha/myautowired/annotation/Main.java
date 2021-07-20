package me.dousha.myautowired.annotation;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ClassContainer classContainer = new ClassContainer();
        classContainer.create(TestClass.class).sendMessage();
    }
}
