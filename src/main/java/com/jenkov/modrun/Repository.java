package com.jenkov.modrun;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class Repository {

    private String rootDir = null;

    //private Map<String, Module> modules = new ConcurrentHashMap<>();

    public Repository(String rootDir) {
        this.rootDir = rootDir;
    }

    public Module createModule(String groupId, String artifactId, String artifactVersion) throws IOException {
        Module module = new Module(groupId, artifactId, artifactVersion);
        module.setClassLoader(new ModuleClassLoader(this, module, createModuleClassStorage(module)));
        buildDependencyGraph(module);
        return module;
    }

    public Class loadClass(Module module, String className) throws ClassNotFoundException {
        if(module.getClassLoader().containsClass(className)){
            return module.getClassLoader().getClassFromThisModule(className);
        }

        //todo loading classes from dependencies can be optimized a bit.
        Class theClass = searchDependenciesForClass(module, className);
        if (theClass != null) {
            return theClass;
        }


        return ClassLoader.getSystemClassLoader().loadClass(className);



    }

    private Class searchDependenciesForClass(Module module, String className) throws ClassNotFoundException {
        // Do a breadth first search of immediate dependencies for the class.
        // A breadth first search allows each module's immediate dependencies to take precedence over
        // transitive dependencies (descendant dependencies further down the dependency graph)
        for(Module dependency : module.getDependencies()){
            if(dependency.getClassLoader().containsClass(className)){
                return module.getClassLoader().getClassFromThisModule(className);
            }
        }


        // Repeat breadth first recursively
        for(Module dependency : module.getDependencies()){
            Class theClass = searchDependenciesForClass(dependency, className);
            if(theClass != null){
                return theClass;
            }
        }
        return null;
    }


    protected void buildDependencyGraph(Module module) {
        System.out.println("Building dependency graph for module: " + module.getFullName());

        List<Dependency> dependencies = readDependenciesForModule(module);

        List<Module> moduleDependencies = new ArrayList<Module>();

        for(Dependency dependency : dependencies) {
            if(dependency.isRuntimeDependency()){
                moduleDependencies.add(new Module(dependency.groupId, dependency.artifactId, dependency.version));
            }
        }

        module.setDependencies(moduleDependencies);

        for(Module moduleDepdendency : moduleDependencies){
            buildDependencyGraph(moduleDepdendency);
        }
    }

    protected List<Dependency> readDependenciesForModule(Module module){
        String modulePomPath = createModulePomPath(module);

        try(Reader reader = new InputStreamReader(new FileInputStream(modulePomPath), "UTF-8")){
            return ModuleDependencyReader.readDependencies(reader);
        } catch (UnsupportedEncodingException e) {
            throw new ModRunException("Error reading dependencies for module " + module.getFullName(), e);
        } catch (FileNotFoundException e) {
            throw new ModRunException("Error reading dependencies for module " + module.getFullName(), e);
        } catch (IOException e) {
            throw new ModRunException("Error reading dependencies for module " + module.getFullName(), e);
        }
    }




    private String createModulePomPath(Module module) {
        return this.rootDir + "/" + module.getFullName()
                            + "/" + module.getArtifactId()
                            + "-" + module.getVersion() + ".pom";
    }

    private ClassStorageZipFileImpl createModuleClassStorage(Module module) throws IOException {
        String moduleStoragePath = createFullModulePath(module);

        ClassStorageZipFileImpl classStorage = new ClassStorageZipFileImpl(moduleStoragePath);
        if(!classStorage.exists()){
            throw new ModRunException("Module not found - looked at: " + moduleStoragePath );
        }
        return classStorage;
    }

    private String createFullModulePath(Module module) {
        return this.rootDir + "/" + module.getFullName() + "/" + module.getArtifactId() + "-" + module.getVersion() + ".jar";
    }


}
