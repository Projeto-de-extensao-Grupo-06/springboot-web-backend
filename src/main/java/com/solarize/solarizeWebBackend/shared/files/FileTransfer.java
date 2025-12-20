package com.solarize.solarizeWebBackend.shared.files;
import com.solarize.solarizeWebBackend.modules.projectFile.SavedFileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

public class FileTransfer {

    /**
     * Salva o MultipartFile em 'targetDir' e retorna metadados + SHA-256.
     * - Cria o diretório se não existir
     * - Sanitiza o nome de arquivo
     * - Calcula o hash enquanto grava (sem ler duas vezes)
     */
    public static SavedFileInfo saveAndHash(MultipartFile file, Path targetDir) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio ou nulo");
        }

        // Garante diretório
        Files.createDirectories(targetDir.toAbsolutePath().normalize());

        // Nome seguro (evita path traversal)
        String original = file.getOriginalFilename() == null ? "upload.bin" : file.getOriginalFilename();
        String safeName = Paths.get(original).getFileName().toString().replaceAll("[^a-zA-Z0-9._-]", "_");

        // Se quiser evitar sobrescrita, gere um sufixo único:
        // safeName = System.currentTimeMillis() + "_" + safeName;

        Path dest = targetDir.resolve(safeName).normalize();

        // Hash + gravação em uma passada
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (InputStream in = file.getInputStream();
             DigestInputStream din = new DigestInputStream(in, md);
             OutputStream out = Files.newOutputStream(dest, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            din.transferTo(out);
        }

        String sha256 = HexFormat.of().formatHex(md.digest());

        return new SavedFileInfo(
                dest.toAbsolutePath(),
                original,
                file.getContentType() != null ? file.getContentType() : "application/octet-stream",
                file.getSize(),
                sha256
        );
    }

//    /** Retorna um diretório tmp multiplataforma: /tmp/uploads (Linux) ou %TEMP%|uploads (Windows) */
    public static Path defaultTmpUploadsDir() throws Exception {
        String tmp = System.getProperty("java.io.tmpdir");
        Path dir = Paths.get(tmp, "uploads");
        Files.createDirectories(dir);
        return dir.toAbsolutePath().normalize();
    }
}


