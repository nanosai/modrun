package com.jenkov.modrun;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class ModuleClassLoaderTest {


    @Test
    public void test() throws ClassNotFoundException {
        ClassStorageDirectoryImpl classStorage = new ClassStorageDirectoryImpl("target/classes/");
        ModuleClassLoader classLoader = new ModuleClassLoader(classStorage, ClassLoader.getSystemClassLoader());

        String className = "com.jenkov.modrun.mod.TestPojo";
        Class aClass = classLoader.loadClass(className);

        System.out.println("aClass.getName() = " + aClass.getName());
    }


    @Test
    public void test2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        loadAndRun("mod-v1/");
        loadAndRun("mod-v2/");

    }

    private static void loadAndRun(String modName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ClassStorageDirectoryImpl modClassStorage = new ClassStorageDirectoryImpl(modName);
        ModuleClassLoader modClassLoader    = new ModuleClassLoader(modClassStorage);

        ClassStorageDirectoryImpl rootClassStorage = new ClassStorageDirectoryImpl("rootmod/");
        ModuleClassLoader rootClassLoader = new ModuleClassLoader(rootClassStorage, modClassLoader);

        Class rootModClass = rootClassLoader.loadClass("com.jenkov.modrun.rootmod.RootModMain");

        Method method = rootModClass.getMethod("main", String[].class);
        method.invoke(null, (Object) new String[]{"arg1", "arg2"});

    }
}
