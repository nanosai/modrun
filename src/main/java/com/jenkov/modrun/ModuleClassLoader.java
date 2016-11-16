package com.jenkov.modrun;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class ModuleClassLoader extends ClassLoader {

    private Repository    repository;
    private Module        module;
    private IClassStorage classStorage;


    private Map<String, Class> loadedClassesMap = new HashMap<>();

    public ModuleClassLoader(Repository repository, Module module, IClassStorage classStorage){
        this.repository   = repository;
        this.module       = module;
        this.classStorage = classStorage;
    }

    public boolean containsClass(String className){
        return this.classStorage.containsClass(className);
    }

    public Class getClassFromThisModule(String className) throws ClassNotFoundException {
        if(this.loadedClassesMap.containsKey(className)){
            return this.loadedClassesMap.get(className);
        }

        if(this.classStorage.containsClass(className)){
            Class theClass = readClassFromClassStorage(className);
            this.loadedClassesMap.put(className, theClass);
            return theClass;
        }

        return null;  //if this module does not contain the class - return null;
    }


    public Class loadClass(String className) throws ClassNotFoundException {
        Class theClass = getClassFromThisModule(className);

        if(theClass != null){
            return theClass;
        }

        return this.repository.loadClass(this.module, className);

    }

    private Class readClassFromClassStorage(String className) throws ClassNotFoundException {
        byte[] classBytes = new byte[0];
        try {
            classBytes = this.classStorage.readClassBytes(className);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error reading class from class storage (" + className + ")", e);
        }
        return defineClass(className, classBytes, 0, classBytes.length);
    }
}
