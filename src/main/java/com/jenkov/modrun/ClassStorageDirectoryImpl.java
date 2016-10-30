package com.jenkov.modrun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jjenkov on 24-10-2016.
 */
public class ClassStorageDirectoryImpl implements IClassStorage {

    protected String classpath;

    public ClassStorageDirectoryImpl(String classpath) {
        this.classpath = classpath;
    }

    @Override
    public boolean exists() {
        return new File(this.classpath).exists();
    }

    public boolean containsClass(String className){
        Path path = toFullPath(className);
        File classFile = path.toFile();
        return classFile.exists();
    }


    @Override
    public byte[] readClassBytes(String className) throws IOException {
        Path pathToClass = toFullPath(className);
        File classFile = pathToClass.toFile();

        int fileLength = (int) classFile.length();

        byte[] classBytes = new byte[fileLength];

        try(FileInputStream classInput = new FileInputStream(classFile)){
            classInput.read(classBytes);
        }

        return classBytes;
    }

    private Path toFullPath(String className) {
        String pathToClass     = className.replace(".", "/");
        String fullPathToClass = this.classpath + pathToClass + ".class";

        return Paths.get(fullPathToClass);
    }
}
