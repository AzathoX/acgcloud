package org.nrocn.lib.utils;

import java.io.BufferedInputStream;
import java.io.IOException;

public abstract class BaseString {

    public static String GETTER = "get";
    public static String SETTER = "set";

    public static String inputStream(BufferedInputStream ioStream,int size){
        String contain = "";
        byte[] content  = new byte[size];
        int fileSize = 0;
        try{
            while ((fileSize = ioStream.read(content)) != -1){
                contain += new String(content);
            }
            return contain;
        }
        catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                ioStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contain;

    }

    /**
     * 传入单词返回首字母
     * @param word
     * @return
     */
    public static String getFirstLetter(String word){
        BaseAssert.strNNull(word,"字符串为空(null)或(''),无法截取");
        String first = word.substring(0, 1);
        return first;
    }


    /**
     * 改变首字母大小
     * @param word
     * @return
     */
    public static  String firstLetter(String word,boolean toUpper){
        String first = getFirstLetter(word);
        if(toUpper){
            first = first.toUpperCase();
        }
        else{
            first = first.toLowerCase();
        }
        String str = first + word.substring(1, word.length());
        return str;
    }


    public  static  String firstLowerLetter(String word){
        return  firstLetter(word,false);
    }

    public static String firstUpperLetter(String word){
        return firstLetter(word,true);
    }
}
