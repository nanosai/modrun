package com.jenkov.modrun;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jjenkov on 30-11-2016.
 */
public class ModRun {


    public static void main(String[] args) {
        if(args.length < 5){
            System.out.println("Wrong number of arguments!");
            System.out.println("Usage:");
            System.out.println("ModRun repositoryDir groupId artifactId artifactVersion MainClassName arg0 arg1 arg2 etc.");

            return;
        }

        String repositoryDir   = args[0];
        String groupId         = args[1];
        String artifactId      = args[2];
        String artifactVersion = args[3];
        String mainClassName   = args[4];

        int noOfArgsToMainMethod = args.length - 5;
        String[] argsToMainMethod = new String[noOfArgsToMainMethod];
        for(int i=0; i<noOfArgsToMainMethod; i++){
            argsToMainMethod[i] = args[5 + i];
        }

        Repository repository = new Repository(repositoryDir);

        Module module = null;
        try {
            module = repository.createModule(groupId, artifactId, artifactVersion);
        } catch (IOException e) {
            System.out.println("Error creating module: " + e.getMessage());
            return;
        }

        Class mainClass = null;

        try {
            mainClass = module.getClass(mainClassName);
        } catch (IOException e) {
            System.out.println("Error creating main class: " + e.getMessage());
            return;
        }

        if(mainClass == null){
            System.out.println("Class " + mainClass + " was not found");
            return;
        }

        Class<String[]> aClass = String[].class;
        Method mainMethod = null;
        try {
            mainMethod = mainClass.getMethod("main", new Class[]{aClass});
        } catch (NoSuchMethodException e) {
            System.out.println("Main class does not contain a main(String[] args) method");
            return;
        }

        try {
            mainMethod.invoke(null, argsToMainMethod);
        } catch (IllegalAccessException e) {
            System.out.println("main(String[] args) method not accessible: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println("Calling main(String[] args) failed: " + e.getMessage());
        }
    }
}
