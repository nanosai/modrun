# ModRun
ModRun can load and run classes directly from Maven repositories and resolve dependencies at runtime. 

ModRun is short for Module Runner. In Maven context a "module" is the same as an artifact in
a specific version.


When running an application with ModRun you just point to the Maven repository containing the
artifact you want to run, then tell ModRun what artifact id, version and main class to run.

ModRun will resolve dependencies at runtime - loading other modules (artifacts) in the correct
versions directly from the Maven repository.

ModRun can even load multiple versions of the same artifact into the same JVM. If an application
depends on module A and module B, and A and B both depend on module C - but in different versions,
ModRun can load one version of C for module A, and another version of C for module B.

ModRun is still in early development, but the early proof-of-concept is working.


## Example
Here is a simple example of how to create a Repository object, load a module and read a class
from it:


    Repository repository = new Repository("test-repo");

    Module module   = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");

    Class  theClass = module.getClass("com.nanosai.a.ComponentA");
    
    Object theObject = theClass.newInstance();
    Method method    = theClass.getMethod("doIt");
    String result    = (String) method.invoke(theObject, new Object[0]);
    
    System.out.println(result);
    




