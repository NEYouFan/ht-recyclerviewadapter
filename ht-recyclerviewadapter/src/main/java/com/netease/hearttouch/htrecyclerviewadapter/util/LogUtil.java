package com.netease.hearttouch.htrecyclerviewadapter.util;

import android.util.Log;

/**
 * Created by zyl06 on 10/20/15.
 */
public class LogUtil {
    private static final String sTag = "HTRecycleView";

    public static void logW(String msg){
        Log.w(sTag, msg);
    }

    public static void logE(String msg){
        Log.e(sTag, "error running: " + msg);
    }

    public static void logPrintStackTrace(StackTraceElement[] ste){
        StringBuilder sb = new StringBuilder();
        for(StackTraceElement s : ste){
            sb.append('\n');
            sb.append(s.toString());
        }
        logE(sb.toString());
    }

    public static void logD(String msg){
        Log.d(sTag, msg);
    }

    public static void i(String Tag, String msg) {
        Log.i(Tag, msg);
    }

    public static void e(String Tag, String msg) {
        Log.e(Tag, msg);
    }

    public static void d(String Tag, String msg) {
        Log.d(Tag, msg);
    }
}
