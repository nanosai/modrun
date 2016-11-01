package com.jenkov.modrun;

/**
 * Created by jjenkov on 31-10-2016.
 */
public class Dependency {
    public String groupId;
    public String artifactId;
    public String version;
    public String scope;

    public String toString() {
        return "(groupId: " + groupId + ", artifactId: " + artifactId + ", version: " + version + ", scope: " + scope + ")";
    }
}
