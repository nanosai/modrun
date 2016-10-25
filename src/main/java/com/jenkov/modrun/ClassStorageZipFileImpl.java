package com.jenkov.modrun;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by jjenkov on 25-10-2016.
 */
public class ClassStorageZipFileImpl implements IClassStorage {

    private String  zipFilePath;
    private ZipFile zipFile;

    public ClassStorageZipFileImpl(String zipFilePath) throws IOException {
        this.zipFilePath = zipFilePath;
        this.zipFile     = new ZipFile(this.zipFilePath);
    }

    @Override
    public boolean containsClass(String className) {
        String classPath = className.replace(".", "/");

        return this.zipFile.getEntry(classPath) != null;
    }

    @Override
    public byte[] readClassBytes(String className) throws IOException {
        String classPath = className.replace(".", "/");

        ZipEntry zipEntry = this.zipFile.getEntry(classPath);
        byte[] classBytes = new byte[(int) zipEntry.getSize()];

        InputStream inputStream = this.zipFile.getInputStream(zipEntry);
        inputStream.read(classBytes);
        return classBytes;
    }
}
