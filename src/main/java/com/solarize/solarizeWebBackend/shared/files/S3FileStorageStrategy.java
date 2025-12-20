package com.solarize.solarizeWebBackend.shared.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@Profile("prod")
public class S3FileStorageStrategy implements FileStorageStrategy {

    private final S3Client s3;
    private final String bucket;

    public S3FileStorageStrategy(
            S3Client s3,
            @Value("${app.storage.s3.bucket}") String bucket) {
        this.s3 = s3;
        this.bucket = bucket;
    }

    @Override
    public void save(String fileName, byte[] content) {
        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build(),
                RequestBody.fromBytes(content)
        );
    }

    @Override
    public byte[] load(String fileName) throws IOException {
        var response = s3.getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build()
        );

        return response.readAllBytes();
    }

    @Override
    public void delete(String fileName) {
        s3.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build()
        );
    }
}
