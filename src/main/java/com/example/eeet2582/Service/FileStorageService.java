package com.example.eeet2582.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileStorageService {

    @Value("${aws.s3.grandmaly-architecture}")
    private String bucketName;

    private final S3Client s3Client;

    public FileStorageService() {
        // You can also use other ways to configure AWS credentials
        this.s3Client = S3Client.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    public void uploadFile(String fileName, MultipartFile file) throws IOException {
        // Read the file into a byte array
        InputStream inputStream = file.getInputStream();
        byte[] bytes = inputStream.readAllBytes();

        // Create a RequestBody object from the byte array
        RequestBody requestBody = RequestBody.fromBytes(bytes);

        // Upload the file to Amazon S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);

        // Check if the upload was successful
        if (putObjectResponse.sdkHttpResponse().statusCode() != 200) {
            throw new IOException("Failed to upload file to Amazon S3");
        }
    }
}
