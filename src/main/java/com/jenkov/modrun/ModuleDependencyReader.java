package com.jenkov.modrun;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjenkov on 31-10-2016.
 */
public class ModuleDependencyReader {

    public static List<Dependency> readDependencies(Reader pomReader){
        List<Dependency> dependencies = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();

        try {
            XMLStreamReader streamReader =
                    factory.createXMLStreamReader(pomReader);

            while(streamReader.hasNext()){
                streamReader.next();
                if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT){
                    String elementName = streamReader.getLocalName();

                    if(elementName.equals("dependency")){
                        Dependency dependency = parseDependency(streamReader);

                        dependencies.add(dependency);

                    }

                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return dependencies;
    }

    public static Dependency parseDependency(XMLStreamReader streamReader) throws XMLStreamException {
        Dependency dependency = new Dependency();


        while(!(streamReader.getEventType() == XMLStreamReader.END_ELEMENT)){
            streamReader.next();

            if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT){
                String elementName = streamReader.getLocalName();

                if("groupId".equals(elementName)){
                    dependency.groupId = streamReader.getElementText();
                    while(streamReader.getEventType() != XMLStreamReader.END_ELEMENT){
                        streamReader.next();
                    }
                    streamReader.next();
                } else if("artifactId".equals(elementName)){
                    dependency.artifactId = streamReader.getElementText();
                    while(streamReader.getEventType() != XMLStreamReader.END_ELEMENT){
                        streamReader.next();
                    }
                    streamReader.next();
                } else if("version".equals(elementName)){
                    dependency.version = streamReader.getElementText();
                    while(streamReader.getEventType() != XMLStreamReader.END_ELEMENT){
                        streamReader.next();
                    }
                    streamReader.next();
                } else if("scope".equals(elementName)){
                    dependency.scope = streamReader.getElementText();
                    while(streamReader.getEventType() != XMLStreamReader.END_ELEMENT){
                        streamReader.next();
                    }
                    streamReader.next();
                } else {
                    while(streamReader.getEventType() != XMLStreamReader.END_ELEMENT){
                        streamReader.next();
                    }
                    streamReader.next();
                }

            }

        }

        return dependency;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String repoRootDir = "D:\\data\\java\\products\\maven\\repository";
        //String filePath = "\\com\\nanosai\\grid-ops\\0.2.0\\grid-ops-0.2.0.pom";
        String filePath = "\\com\\jcraft\\jsch\\0.1.44-1\\jsch-0.1.44-1.pom";

        FileReader reader = new FileReader(repoRootDir + filePath);

        List<Dependency> dependencies = ModuleDependencyReader.readDependencies(reader);



    }
}
