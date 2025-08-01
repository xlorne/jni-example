package com.codingapi.jni;

import java.io.InputStream;
import java.nio.file.StandardCopyOption;


public class HelloWord {

    public native void hello();

    static {
        String path = System.getProperty("user.dir");
        String libPath = path + "/lib/libhello.dylib";
        System.load(libPath);
    }
}
