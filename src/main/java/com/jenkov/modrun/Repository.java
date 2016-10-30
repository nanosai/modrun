package com.jenkov.modrun;

import java.io.File;
import java.io.IOException;
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


    public Module getModule(String groupId, String artifactId, String artifactVersion) throws IOException {
        groupId         = groupId.replace(".", "/");
        artifactId      = artifactId.replace(".", "/");

        String fullModuleName = groupId + "/" + artifactId + "/" + artifactVersion;

        Module module = this.modules.get(fullModuleName);

        if(module != null){
            return module;
        }

        String moduleStoragePath = this.rootDir + "/" + fullModuleName + "/" + artifactId + "-" + artifactVersion + ".jar";
        ClassStorageZipFileImpl classStorage = new ClassStorageZipFileImpl(moduleStoragePath);
        if(!classStorage.exists()){
            throw new ModRunException("Module not found - looked at: " + moduleStoragePath );
        }

        ModuleClassLoader moduleClassLoader = new ModuleClassLoader(classStorage);

        module = new Module(groupId, artifactId, artifactVersion, moduleClassLoader);
        this.modules.put(fullModuleName, module);

        return module;
    }


}
