package com.jenkov.modrun;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class Repository {

    private String rootDir = null;

    private Map<String, Module> modules = new ConcurrentHashMap<>();

    public Repository(String rootDir) {
        this.rootDir = rootDir;
    }


    public Module getModule(String groupId, String artifactId, String artifactVersion){
        String fullModuleName = groupId + "/" + artifactId + "/" + artifactVersion;

        Module module = this.modules.get(fullModuleName);

        if(module == null){
            module = new Module(groupId, artifactId, artifactVersion);
            this.modules.put(fullModuleName, module);
        }

        System.out.println("fullModuleName = " + fullModuleName);

        return module;
    }


}
