package org.nrocn.lib.utils;


public abstract class BaseAssert {
    public static void bjNNull(Object o,String msg){
        assert o != null : msg;
    }

    public static void strNNull(String str,String msg){
        assert str != null && str.length() > 0 : msg;
    }
}
