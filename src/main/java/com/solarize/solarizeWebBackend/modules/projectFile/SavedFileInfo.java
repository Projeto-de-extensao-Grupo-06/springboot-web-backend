package com.solarize.solarizeWebBackend.modules.projectFile;

import java.nio.file.Path;

public record SavedFileInfo(
        Path path,
        String originalName,
        String contentType,
        long size,
        String sha256
) {}