package com.jenkov.modrun;

import java.io.IOException;

/**
 * Created by jjenkov on 24-10-2016.
 */
public interface IClassStorage {

    public boolean containsClass(String className);

    public byte[] readClassBytes(String className) throws IOException;

}
