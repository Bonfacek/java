package com.example.Karanis.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
// REMOVED @Data here to control method order manually
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Place non-LOB fields first
    private String name;
    private String mimeType;

    @Column(name = "file_size_kb")
    private Long videoFileSizeKB;

    @CreationTimestamp
    private LocalDateTime uploadDate;

    // Place the LOB field last
    @Lob
    @Column(name = "data", columnDefinition = "BYTEA")
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    // Manually generated Getters and Setters follow a consistent order
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public Long getVideoFileSizeKB() { return videoFileSizeKB; }
    public void setVideoFileSizeKB(Long videoFileSizeKB) { this.videoFileSizeKB = videoFileSizeKB; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}
