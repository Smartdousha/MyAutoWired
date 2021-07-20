package me.dousha.myautowired.annotation;

public class TestClass {
    @AutoWired
    TestClass2 testClass2;
    @AutoWired
    TestClass3 testClass3;

    public void sendMessage(){
        System.out.println(testClass2);
        System.out.println(testClass3);
    }
}
