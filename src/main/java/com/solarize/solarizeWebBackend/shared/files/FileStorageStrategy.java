package com.solarize.solarizeWebBackend.shared.files;

import java.io.IOException;

public interface FileStorageStrategy{
    void save(String fileName, byte[] content);

    byte[] load(String fileName) throws IOException;

    void delete(String fileName);
}
