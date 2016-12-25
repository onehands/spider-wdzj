package com.stbbd.spider.wdzj.utils;

/**
 * Created by lei on 16-12-21.
 */
public class ClassUtil {
    public static String getClassName(String pkgname){
        String[] arr = pkgname.split("\\.");
        String classname = arr[arr.length - 1];
        return classname.substring(0, 1).toLowerCase() + classname.substring(1);
    }

}
