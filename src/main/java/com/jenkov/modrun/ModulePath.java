package com.jenkov.modrun;


/**
 * Created by jjenkov on 03-12-2016.
 */
public class ModulePath {

    private String groupId         = null;
    private String artifactId      = null;
    private String artifactVersion = null;

    public ModulePath(String groupId, String artifactId, String artifactVersion) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.artifactVersion = artifactVersion;
    }

    public String getModuleJarUrl(String baseUrl){
        return getModuleJarUrl(baseUrl, this.groupId, this.artifactId, this.artifactVersion);
    }

    public String getModulePomUrl(String baseUrl){
        return getModulePomUrl(baseUrl, this.groupId, this.artifactId, this.artifactVersion);
    }

    public String getModuleJarFilePath(String baseDirectoryPath){
        return getModuleJarFilePath(baseDirectoryPath, this.groupId, this.artifactId, this.artifactVersion);
    }

    public String getModulePomFilePath(String baseDirectoryPath){
        return getModulePomFilePath(baseDirectoryPath, this.groupId, this.artifactId, this.artifactVersion);
    }


    public static String getModuleDirectoryPath(String baseDirectoryPath, String groupId, String artifactId, String artifactVersion){
        return
                (baseDirectoryPath.endsWith("/") ? baseDirectoryPath : baseDirectoryPath + "/")
                + groupId.replace('.', '/')
                + "/"
                + artifactId
                + "/"
                + artifactVersion
                + "/"
                ;
    }


    public static String getModulePomUrl(String baseUrl, String groupId, String artifactId, String artifactVersion){
        return getModuleBaseUrl(baseUrl, groupId, artifactId, artifactVersion) + ".pom";
    }

    public static String getModulePomFilePath(String baseDirectoryPath, String groupId, String artifactId, String artifactVersion){
        return getModuleBaseFilePath(baseDirectoryPath, groupId, artifactId, artifactVersion) + ".pom";
    }

    public static String getModuleJarUrl(String baseUrl, String groupId, String artifactId, String artifactVersion){
        return getModuleBaseUrl(baseUrl, groupId, artifactId, artifactVersion) + ".jar";
    }


    public static String getModuleJarFilePath(String baseDirectoryPath, String groupId, String artifactId, String artifactVersion){
        return getModuleBaseFilePath(baseDirectoryPath, groupId, artifactId, artifactVersion) + ".jar";
    }


    public static String getModulePathOnly(String groupId, String artifactId, String artifactVersion){
        return artifactId
                + "/"
                + artifactVersion
                + "/"
                + artifactId
                + "-"
                + artifactVersion
        ;
    }




    public static String getModuleBaseUrl(String baseUrl, String groupId, String artifactId, String artifactVersion){
        String finalUrlBase =
                (baseUrl.endsWith("/") ? baseUrl : baseUrl + "/")
                + groupId.replace('.', '/')
                + "/"
                + getModulePathOnly(groupId, artifactId, artifactVersion)
                ;
        return finalUrlBase;
    }

    public static String getModuleBaseFilePath(String baseDirectoryPath, String groupId, String artifactId, String artifactVersion){
        String finalUrlBase =
                (baseDirectoryPath.endsWith("/") ? baseDirectoryPath : baseDirectoryPath + "/")
                + groupId.replace('.', '/')
                + "/"
                + getModulePathOnly(groupId, artifactId, artifactVersion)
                ;
        return finalUrlBase;
    }


}
