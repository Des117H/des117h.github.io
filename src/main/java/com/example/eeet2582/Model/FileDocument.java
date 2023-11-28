package com.example.eeet2582.Model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDocument {
    private String documentID;
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
