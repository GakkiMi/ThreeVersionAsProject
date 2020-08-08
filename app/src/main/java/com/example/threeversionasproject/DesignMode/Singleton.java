package com.example.threeversionasproject.DesignMode;

/**
 * Created by Ocean on 2020/7/25.
 */

public class Singleton {
    //必需防止外部可以调用构造函数进行实例化，因此构造函数必需私有化
    private Singleton() {
    }

    //饿汉单例
    public static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }


    static class Singleton2 {

        private Singleton2() {
        }

        //懒汉单例
        public static volatile Singleton2 singleton2 = null;

        public static Singleton2 getInstance() {
            if (singleton2 == null) {
                synchronized (Singleton2.class) {
                    if (singleton2 == null) {
                        singleton2 = new Singleton2();
                    }
                }
            }
            return singleton2;
        }


    }


}
