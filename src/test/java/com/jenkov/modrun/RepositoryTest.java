package com.jenkov.modrun;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class RepositoryTest {

    //String repoRootDir    = "D:\\data\\java\\products\\maven\\repository";
    String repoRootDir    = "test-repo";

    Repository repository = new Repository(repoRootDir);

    @Test
    public void testBuildDependencies() throws IOException {
        Module module1 = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");
        repository.buildDependencyGraph(module1);

        List<Module> dependencies1 = module1.getDependencies();

        assertEquals(1, dependencies1.size());

        Module dependency1 = dependencies1.get(0);

        assertEquals("com.nanosai", dependency1.getGroupId());
        assertEquals("ModRunDepC" , dependency1.getArtifactId());
        assertEquals("1.0.0"      , dependency1.getVersion());

        Module module2 = repository.createModule("com.nanosai", "ModRunDepB", "1.0.0");

        List<Module> dependencies2 = module2.getDependencies();

        assertEquals(1, dependencies2.size());

        Module dependency2 = dependencies2.get(0);

        assertEquals("com.nanosai", dependency2.getGroupId());
        assertEquals("ModRunDepC" , dependency2.getArtifactId());
        assertEquals("2.0.0"      , dependency2.getVersion());

    }

    @Test
    public void test() throws IOException, ClassNotFoundException {

        Module module1 = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");
        Module module2 = repository.createModule("com.nanosai", "ModRunDepA", "1.0.0");

        assertNotSame(module1, module2);

        Class theClass = repository.loadClass(module1, "com.nanosai.modrun.a.ComponentA");

        assertNotNull(theClass);

        assertEquals("com.nanosai.modrun.a.ComponentA", theClass.getName());
    }



}
