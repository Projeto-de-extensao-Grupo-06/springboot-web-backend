package com.solarize.solarizeWebBackend.shared.files;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Profile("dev")
public class LocalFileStorageStrategy implements FileStorageStrategy{


    private final Path root = Paths.get(System.getProperty("java.io.tmpdir"), "uploads");


    @Override
    public void save(String fileName, byte[] content) {
        try {
            Files.createDirectories(root);
            Files.write(root.resolve(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    @Override
    public byte[] load(String fileName) {
        try {
            return Files.readAllBytes(root.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.deleteIfExists(root.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover arquivo", e);
        }
    }

}
