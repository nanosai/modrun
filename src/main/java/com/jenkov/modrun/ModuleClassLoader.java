package com.jenkov.modrun;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class ModuleClassLoader extends ClassLoader {

    private Module module = null;

    public ModuleClassLoader(Module module){
        this.module = module;
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        System.out.println("loadClass: " + className);
        try {
            Class theClass = this.module.getClass(className);
            if(theClass == null){
                throw new ClassNotFoundException("ModRun could not find class: " + className);
            }
            return theClass;
        } catch (IOException e) {
            throw new ClassNotFoundException("ModRun failed to load class class: " + className, e);
        }
    }

    public Class defClass(String className, byte[] classBytes, int offset, int length){
        System.out.println("defClass:  " + className);
        return defineClass(className, classBytes, offset, length);
    }


}
