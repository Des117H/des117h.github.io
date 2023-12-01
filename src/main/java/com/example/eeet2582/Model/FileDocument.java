package com.example.eeet2582.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDocument {
    private String documentID;
    private String filename;
    private String downloadUrl;
    private String uploadedAt;
    private String userId;

    // Getters and setters for file metadata attributes
    public FileDocument(String documentID, String filename, String downloadUrl, String uploadedAt, String userId) {
        this.documentID = documentID;
        this.filename = filename;
        this.downloadUrl = downloadUrl;
        this.uploadedAt = uploadedAt;
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "FileMetadata{" +
                "filename='" + filename + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", userId=" + userId +
                '}';
}
}
