package com.progressoft.jip.gateway;

public interface FileManager<T> {

    public T loadFileContent();

    void updateFileContent(Object content);

}
