package com.github.xaatw0.sql2bean.beanmaker;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class DynamicJavaSourceCodeObject extends SimpleJavaFileObject {

    private final String code;

    public DynamicJavaSourceCodeObject(String name, String code) {
        super(URI.create("string:///" + name.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code ;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)throws IOException {
        return code;
    }
}
