package com.example.eeet2582.Model;

import java.time.LocalDateTime;

public class FileDocument {

    private String filename;
    private String contentType;
    private String downloadUrl;
    private LocalDateTime uploadedAt;
    private Long userId;

    // Getters and setters for file metadata attributes
    @Override
    public String toString() {
        return "FileMetadata{" +
                "filename='" + filename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", userId=" + userId +
                '}';
}
}
