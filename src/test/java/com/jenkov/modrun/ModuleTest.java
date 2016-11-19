package com.jenkov.modrun;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by jjenkov on 18-11-2016.
 */
public class ModuleTest {

    @Test
    public void testFindClass() throws IOException {
        Repository repository = new Repository("test-repo");

        Module modRunDepA = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");

        assertNotNull(modRunDepA);

        Module classOwner1 = modRunDepA.findClass("com.nanosai.modrun.a.ComponentA");
        assertSame(modRunDepA, classOwner1);

        Module classOwner2 = modRunDepA.findClass("com.nanosai.modrun.c.DependencyC");
        assertNotSame(classOwner1, classOwner2);
        assertSame(classOwner2, modRunDepA.getDependencies().get(0));
    }


    @Test
    public void testGetClass() throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Repository repository = new Repository("test-repo");

        Module modRunDepA = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");

        String className1 = "com.nanosai.modrun.a.ComponentA";
        Class  theClass1  = modRunDepA.getClassFromThisModule(className1);
        assertEquals(className1, theClass1.getName());

        Object theObject1 = theClass1.newInstance();
        Method method1 = theClass1.getMethod("doIt");
        String result1 = (String) method1.invoke(theObject1, new Object[0]);

        assertEquals("This is ObjectA - Version 1", result1);

        //repeat test - but for ModRunDepB instead.
        Module modRunDepB = repository.createModule("com.nanosai", "ModRunDepB", "1.0.0");

        String className2 = "com.nanosai.modrun.b.ComponentB";
        Class  theClass2  = modRunDepB.getClassFromThisModule(className2);
        assertEquals(className2, theClass2.getName());

        Object theObject2 = theClass2.newInstance();
        Method method2 = theClass2.getMethod("doIt");
        String result2 = (String) method2.invoke(theObject2, new Object[0]);

        assertEquals("A text from ObjectB - Version 2", result2);

        //test getting the same class twice
        Class theClass2_2 = modRunDepB.getClass(className2);
        assertNotNull(theClass2_2);
        assertSame(theClass2_2, theClass2);
        assertEquals(2, modRunDepA.getLoadedClasses().size());
        assertEquals(2, modRunDepB.getLoadedClasses().size());
    

    }

}
