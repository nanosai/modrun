package com.jenkov.modrun;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class RepositoryTest {

    String repoRootDir = "D:\\data\\java\\products\\maven\\repository";
    String filePath = "\\com\\nanosai\\grid-ops\\0.2.0\\grid-ops-0.2.0.jar";

    Repository repository = new Repository("D:\\data\\java\\products\\maven\\repository");

    @Test
    public void test() throws IOException, ClassNotFoundException {

        Module module1 = repository.getModule("com.nanosai", "grid-ops", "0.2.0");
        Module module2 = repository.getModule("com.nanosai", "grid-ops", "0.2.0");

        assertSame(module1, module2);

        Class aClass = module1.loadClass("com.nanosai.gridops.ion.read.IonReader");

        assertNotNull(aClass);

        assertEquals("com.nanosai.gridops.ion.read.IonReader", aClass.getName());
    }


    @Test
    public void testZip() throws IOException {
        ZipFile zipFile = new ZipFile(repoRootDir + filePath);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if(entry.isDirectory()){
                System.out.println("dir  : " + entry.getName());
            } else {
                System.out.println("file : " + entry.getName());
            }
        }
    }


    @Test
    public void testZip2() throws IOException {
        ZipFile zipFile = new ZipFile(repoRootDir + filePath);

        ZipEntry entry = zipFile.getEntry("com/nanosai/gridops/host/Host.class");

        System.out.println("entry.getName() = " + entry.getName());
    }
}
