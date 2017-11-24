package com.jenkov.modrun;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jjenkov on 02-12-2016.
 */
public class ModuleDownloader {

    private String remoteRepositoryBaseUrl;
    private String localRepositoryBaseFilePath;

    public ModuleDownloader(String remoteRepositoryBaseUrl, String localRepositoryBaseFilePath) {
        this.remoteRepositoryBaseUrl = remoteRepositoryBaseUrl;
        if(!this.remoteRepositoryBaseUrl.endsWith("/")){
            this.remoteRepositoryBaseUrl += "/";
        }
        this.localRepositoryBaseFilePath = localRepositoryBaseFilePath;
        if(!this.localRepositoryBaseFilePath.endsWith("/")){
            this.localRepositoryBaseFilePath += "/";
        }
    }



    public void download(String groupId, String artifactId, String artifactVersion) throws IOException {
        File jarFileDirectory = new File(ModulePath.getModuleDirectoryPath(this.localRepositoryBaseFilePath, groupId, artifactId, artifactVersion));
        if(!jarFileDirectory.exists()){
            jarFileDirectory.mkdirs();
        }

        File jarFile = new File(ModulePath.getModuleJarFilePath(this.localRepositoryBaseFilePath, groupId, artifactId, artifactVersion));
        URL  jarUrl  = new URL(ModulePath.getModuleJarUrl(this.remoteRepositoryBaseUrl, groupId, artifactId, artifactVersion));


        try(FileOutputStream jarFileOutput = new FileOutputStream(jarFile); InputStream urlInput = jarUrl.openConnection().getInputStream()) {
            int data = urlInput.read();
            while(data != -1){
                jarFileOutput.write(data);
                data = urlInput.read();
            }
        }
        File pomFile = new File(ModulePath.getModulePomFilePath(this.localRepositoryBaseFilePath, groupId, artifactId, artifactVersion));
        URL  pomUrl  = new URL (ModulePath.getModulePomUrl(this.remoteRepositoryBaseUrl, groupId, artifactId, artifactVersion));

        try(FileOutputStream pomFileOutput = new FileOutputStream(pomFile); InputStream urlInput = pomUrl.openConnection().getInputStream()) {
            int data = urlInput.read();
            while(data != -1){
                pomFileOutput.write(data);
                data = urlInput.read();
            }
        }
    }
}
