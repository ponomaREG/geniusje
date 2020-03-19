package com.msulov.geniusje.Logging;

import android.util.Log;

public class Logging {
    public static  void log(String tag,String text){
        Log.d(tag,text);
    }
    public static void log(String tag,int text){
        Log.d(tag,text+"");
    }
    public static void log(String tag,long text){
        Log.d(tag,text +"");
    }
}
