package com.jenkov.modrun;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class ModuleClassLoader extends ClassLoader {

    private ClassLoader systemClassLoader;

    private IClassStorage classStorage;
    private ModuleClassLoader[] dependencyClassLoaders;

    private Map<String, Class> loadedClassesMap = new HashMap<>();

    public ModuleClassLoader(IClassStorage classStorage){
        this.classStorage = classStorage;
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
        this.dependencyClassLoaders = new ModuleClassLoader[0];
    }

    public ModuleClassLoader(IClassStorage classStorage, ModuleClassLoader ... dependencyClassLoaders){
        this.classStorage = classStorage;
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
        this.dependencyClassLoaders = dependencyClassLoaders;
    }

    public ModuleClassLoader(IClassStorage classStorage, ClassLoader systemClassLoader) {
        this.systemClassLoader = systemClassLoader;
        this.classStorage = classStorage;
    }

    public ModuleClassLoader(IClassStorage classStorage, ClassLoader systemClassLoader, ModuleClassLoader ... dependencyClassLoaders) {
        this.systemClassLoader = systemClassLoader;
        this.classStorage = classStorage;
        this.dependencyClassLoaders = dependencyClassLoaders;
    }



    public Class resolveClass(String className) throws ClassNotFoundException {
        if(this.loadedClassesMap.containsKey(className)){
            return this.loadedClassesMap.get(className);
        }

        boolean containsClass = this.classStorage.containsClass(className);
        if(containsClass) {
            Class theClass = null;
            try {
                theClass = readClassFromClassStorage(className);
            } catch (IOException e) {
                throw new ClassNotFoundException("Error reading class from class storage: " + e.getMessage(), e);
            }
            this.loadedClassesMap.put(className, theClass);
            return theClass;
        }

        if(dependencyClassLoaders != null){
            for(int i=0; i<this.dependencyClassLoaders.length; i++){
                Class theClass = this.dependencyClassLoaders[i].resolveClass(className);
                if(theClass != null){
                    return theClass;
                }
            }
        }

        return null;
    }


    public Class loadClass(String className) throws ClassNotFoundException {
        Class classObj = resolveClass(className);

        if(classObj != null){
            return classObj;
        }

        return this.systemClassLoader.loadClass(className);
    }

    private Class readClassFromClassStorage(String className) throws IOException {
        byte[] classBytes = this.classStorage.readClassBytes(className);
        return defineClass(className, classBytes, 0, classBytes.length);
    }
}
