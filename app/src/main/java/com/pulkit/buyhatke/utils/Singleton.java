package com.pulkit.buyhatke.utils;

import com.pulkit.buyhatke.pojo.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class Singleton {

    public HashMap<String, ArrayList<Message>> getMessageHashMap() {
        return mMessageHashMap;
    }

    public void setMessageHashMap(HashMap<String, ArrayList<Message>> pMessageHashMap) {
        mMessageHashMap = pMessageHashMap;
    }

    private HashMap<String, ArrayList<Message>> mMessageHashMap = new HashMap<>();

    private static Singleton sSingleton;

    private Singleton() {
        //private default constructor
    }

    public static synchronized Singleton getInstance() {
        if (sSingleton == null) //if none created
            sSingleton = new Singleton(); //create one
        return sSingleton; //return it
    }

    public void print(String s) {
        System.out.println(s);
    }

}
