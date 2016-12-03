package com.jenkov.modrun;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jjenkov on 03-12-2016.
 */
public class ModulePathTest {

    String baseUrl           = "http://repo1.maven.org/maven2";
    String baseDirectoryPath = "test-repo";
    String groupId           = "com.nanosai";
    String artifactId        = "grid-ops";
    String artifactVersion   = "1.0.0";


    @Test
    public void testInstanceMethods() {

        ModulePath modulePath = new ModulePath(groupId, artifactId, artifactVersion);

        String expectedBaseUrl      = baseUrl           + "/" + groupId.replace('.', '/')
                + "/" + artifactId + "/" + artifactVersion + "/" + artifactId + "-" + artifactVersion;
        String expectedBaseFilePath = baseDirectoryPath + "/" + groupId.replace('.', '/')
                + "/" + artifactId + "/" + artifactVersion + "/" + artifactId + "-" + artifactVersion;

        assertEquals(expectedBaseFilePath + ".jar", modulePath.getModuleJarFilePath(baseDirectoryPath));
        assertEquals(expectedBaseUrl      + ".jar", modulePath.getModuleJarUrl     (baseUrl));
        assertEquals(expectedBaseFilePath + ".pom", modulePath.getModulePomFilePath(baseDirectoryPath));
        assertEquals(expectedBaseUrl      + ".pom", modulePath.getModulePomUrl     (baseUrl));
    }


    @Test
    public void testGetModuleBaseFilePath() {
        String result =
                ModulePath.getModuleBaseFilePath(baseDirectoryPath, groupId, artifactId, artifactVersion);

        String expected = baseDirectoryPath + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion;

        assertEquals(expected, result);
    }


    @Test
    public void testGetModuleBaseUrl() {
        String result =
                ModulePath.getModuleBaseUrl(baseUrl, groupId, artifactId, artifactVersion);

        String expected = baseUrl + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion;

        assertEquals(expected, result);
    }


    @Test
    public void testGetModuleJarUrl() {
        String result =
                ModulePath.getModuleJarUrl(baseUrl, groupId, artifactId, artifactVersion);

        String expected = baseUrl + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion + ".jar";

        assertEquals(expected, result);
    }


    @Test
    public void testGetModuleJarFilePath() {
        String result =
                ModulePath.getModuleJarFilePath(baseDirectoryPath, groupId, artifactId, artifactVersion);

        String expected = baseDirectoryPath + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion + ".jar";

        assertEquals(expected, result);
    }


    @Test
    public void testGetModulePomUrl() {
        String result =
                ModulePath.getModulePomUrl(baseUrl, groupId, artifactId, artifactVersion);

        String expected = baseUrl + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion + ".pom";

        assertEquals(expected, result);
    }


    @Test
    public void testGetModulePomFilePath() {
        String result =
                ModulePath.getModulePomFilePath(baseDirectoryPath, groupId, artifactId, artifactVersion);

        String expected = baseDirectoryPath + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + artifactVersion
                + "/" + artifactId + "-" + artifactVersion + ".pom";

        assertEquals(expected, result);
    }


}
