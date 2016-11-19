package com.jenkov.modrun;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Module {

    private String fullName;

    private String groupId;
    private String artifactId;
    private String version;

    private IClassStorage     classStorage;
    private ClassLoader       rootClassLoader;
    private ModuleClassLoader classLoader;

    private List<Module> dependencies;

    public Module(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;

        this.fullName = groupId.replace(".", "/") + "/" + artifactId.replace(".", "/") + "/" + version;
    }

    public String getFullName() {
        return fullName;
    }
    public String getGroupId() {
        return groupId;
    }
    public String getArtifactId() {
        return artifactId;
    }
    public String getVersion() {
        return version;
    }

    public ClassLoader getRootClassLoader() { return rootClassLoader;  }
    public void setRootClassLoader(ClassLoader rootClassLoader) { this.rootClassLoader = rootClassLoader; }

    public void setClassLoader(ModuleClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    public ModuleClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassStorage(IClassStorage classStorage) {
        this.classStorage = classStorage;
    }
    public IClassStorage getClassStorage() {
        return classStorage;
    }

    public List<Module> getDependencies() {
        return dependencies;
    }
    public void setDependencies(List<Module> dependencies) {
        this.dependencies = dependencies;
    }



    public Class getClass(String className) throws IOException {
        Module ownerModule = findClass(className);

        if(ownerModule != null){
            return ownerModule.getClassFromThisModule(className);
        }

        //could be a core Java class - try the rootClassLoader.
        if(this.getRootClassLoader() != null){
            try {
                Class targetClass = this.getRootClassLoader().loadClass(className);
                if(targetClass != null){
                    return targetClass;
                }
            } catch (ClassNotFoundException e) {
                //ignore - we just return null instead.
            }
        }
        return null;

    }

    public Class getClassFromThisModule(String className) throws IOException {
        if(containsClass(className)){
            byte[] bytes = getClassStorage().readClassBytes(className);
            return getClassLoader().defClass(className, bytes, 0, bytes.length);
        }

        return null;
    }

    public Module findClass(String className) {
        if(containsClass(className)){
            return this;
        }
        return findClassInDependencies(className);
    }

    public Module findClassInDependencies(String className) {
        for (int i = 0; i < this.dependencies.size(); i++) {
            Module dependency = this.dependencies.get(i);
            if (dependency.containsClass(className)) {
                return dependency;
            }
        }

        for (int i = 0; i < this.dependencies.size(); i++) {
            Module dependency = this.dependencies.get(i);
            Module targetModule = dependency.findClassInDependencies(className);
            if (targetModule != null) {
                return targetModule;
            }
        }

        return null;
    }

    public boolean containsClass(String className) {
        if(this.classStorage == null){
            throw new NullPointerException("No IClassStorage implementation set on Module");
        }

        return this.classStorage.containsClass(className);
    }
}
