package com.stbbd.spider.framewark.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 朱国印 on 16-12-12.
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static <T> T createInstance(String className, Object... args) {
        Class<T> tClass = null;
        try {
            tClass = (Class<T>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("没有找到对应的class：" + className, e);
            return null;
        }
        return createInstance(tClass, args);
    }

    public static <T> T createInstance(Class<T> c, Object... args) {
        T instance = null;
        try {
            Class<?>[] parameterTypes;
            if (args != null && args.length > 0) {
                parameterTypes = new Class[args.length];
                int i = 0;
                for (Object o : args) {
                    parameterTypes[i++] = o.getClass();
                }
            } else {
                parameterTypes = new Class[0];
            }
            Constructor constructor = c.getConstructor(parameterTypes);
            instance = (T) constructor.newInstance(args);
        } catch (InstantiationException e) {
            logger.error("创建实例出错", e);
        } catch (IllegalAccessException e) {
            logger.error("创建实例出错", e);
        } catch (NoSuchMethodException e) {
            logger.error("创建实例出错", e);
        } catch (InvocationTargetException e) {
            logger.error("创建实例出错", e);
        }
        return instance;
    }
}
