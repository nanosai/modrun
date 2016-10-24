package com.jenkov.modrun;

/**
 *
 */
public class Module {

    private String groupId;
    private String artifactId;
    private String version;

    public Module(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    private ModuleClassLoader moduleClassLoader;

}
