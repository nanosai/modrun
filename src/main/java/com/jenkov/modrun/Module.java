package com.jenkov.modrun;

/**
 *
 */
public class Module {

    private String groupId;
    private String artifactId;
    private String version;

    private ModuleClassLoader classLoader;

    public Module(String groupId, String artifactId, String version, ModuleClassLoader classLoader) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;

        this.classLoader = classLoader;
    }



    public Class loadClass(String className) throws ClassNotFoundException {
        Class theClass = this.classLoader.resolveClass(className);

        return theClass;
    }
}
