# ModRun - Modularity for Java Without Jigsaw
ModRun can load and run classes directly from Maven repositories and resolve dependencies at runtime.
For a more complete tutorial, read this:

[ModRun Tutorial]()http://tutorials.jenkov.com/modrun/index.html)

ModRun is short for Module Runner. In Maven context a "module" is an artifact in
a specific version.


When running an application with ModRun you just point to the Maven repository containing the
artifact you want to run, then tell ModRun what artifact id, version and main class to run.

ModRun will resolve dependencies at runtime - loading other modules (artifacts) in the correct
versions directly from the Maven repository.

ModRun can even load multiple versions of the same artifact into the same JVM. If an application
depends on module A and module B, and A and B both depend on module C - but in different versions,
ModRun can load one version of C for module A, and another version of C for module B.


## Command Line Example:

    java -cp ModRun-0.9.0.jar com.nanosai.modrun.ModRun test-repo com.nanosai myapp 1.0.0 com.nanosai.myapp.Main arg1 arg2 arg3 


Explanation:

    args[0] = rest-repo     // The root directory of the Maven repo
    args[1] = com.nanosai   // The groupId of the Maven artifact containing the main class.
    args[2] = myapp         // The artifactId of the Maven artifact containing the main class.
    args[3] = 1.0.0         // The artifact version of the Maven artifact containing the main class.
    args[4] = com.nanosai.myapp.Main // The main class you want ModRun to run.
    args[5] = arg1          // First argument to main class.
    args[6] = arg2          // Second argument to main class.
    args[7] = arg3          // Third argument to main class.
    args[N] = argN          // Nth argument to main class.


## Embedded Example 1:

    String mavenRepo       = "test-repo";
    String groupId         = "com.nanosai";
    String artifactId      = "myapp";
    String artifactVersion = "1.0.0";
    String mainClassName   = "com.nanosai.myapp.Main";
    String arg1            = "arg1";
    String arg2            = "arg2";
    String arg3            = "arg3";

    ModRun.main(new String[]{ 
        mavenRepo, groupId, artifactId, artifactVersion, mainClassName, arg1, arg2, arg3
    });


## Embedded Example 2:
This example shows how to interact directly with the core ModRun classes, in case you want more
control over the class loading.

Here is a simple example of how to create a Repository object, load a module and read a class
from it:


    Repository repository = new Repository("test-repo");

    Module module   = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");

    Class  theClass = module.getClass("com.nanosai.a.ComponentA");
    
    Object theObject = theClass.newInstance();
    Method method    = theClass.getMethod("doIt");
    String result    = (String) method.invoke(theObject, new Object[0]);
    
    System.out.println(result);
    




