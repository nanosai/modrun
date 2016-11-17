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
        Repository repository = new Repository("test-repo");

        Module module = new Module("com.nanosai", "ModRun", "1.0.0");

        ClassStorageDirectoryImpl classStorage = new ClassStorageDirectoryImpl("target/classes/");
        ModuleClassLoader classLoader = new ModuleClassLoader(repository, module, classStorage);

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
        Repository repository = new Repository("test-repo");

        Module module = new Module("com.nanosai", "ModRun", "1.0.0");

        ClassStorageDirectoryImpl modClassStorage = new ClassStorageDirectoryImpl(modName);
        ModuleClassLoader modClassLoader    = new ModuleClassLoader(repository, module, modClassStorage);

        ClassStorageDirectoryImpl rootClassStorage = new ClassStorageDirectoryImpl("rootmod/");
        ModuleClassLoader rootClassLoader = new ModuleClassLoader(repository, module, rootClassStorage);

        Class rootModClass = rootClassLoader.loadClass("com.jenkov.modrun.rootmod.RootModMain");

        Method method = rootModClass.getMethod("main", String[].class);
        method.invoke(null, (Object) new String[]{"arg1", "arg2"});

    }
}
