package com.jenkov.modrun;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jjenkov on 02-12-2016.
 */
public class ModuleDownloaderTest {


    @Test
    public void test() throws IOException {
        String remoteRepoBaseUrl = "http://repo1.maven.org/maven2/";
        String localRepoBaseFilePath  = "test-repo";

        ModuleDownloader moduleDownloader = new ModuleDownloader(remoteRepoBaseUrl, localRepoBaseFilePath);

        String groupId         = "junit";
        String artifactId      = "junit";
        String artifactVersion = "4.12";

        moduleDownloader.download(groupId, artifactId, artifactVersion);

    }
}
