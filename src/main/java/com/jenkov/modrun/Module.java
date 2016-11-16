package com.jenkov.modrun;

import java.util.List;

/**
 *
 */
public class Module {

    private String fullModuleName;

    private String groupId;
    private String artifactId;
    private String version;

    private ModuleClassLoader classLoader;

    private List<Module> dependencies;

    public Module(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;

        this.fullModuleName = groupId.replace(".", "/") + "/" + artifactId.replace(".", "/") + "/" + version;

        this.classLoader = classLoader;
    }

    public String getFullName() {
        return fullModuleName;
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


    public void setClassLoader(ModuleClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ModuleClassLoader getClassLoader() {
        return classLoader;
    }


    public List<Module> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Module> dependencies) {
        this.dependencies = dependencies;
    }
}
