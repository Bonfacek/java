package com.example.Karanis.DTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing essential metadata for a video.
 * Uses a Java Record for conciseness and immutability.
 */
public record VideoMetadataDTO(
        Long id,              // Unique identifier for the video
        String name,          // The title/name of the video
        String mimeType,      // Content type (e.g., video/mp4)
        Long fileSizeKB,      // Size of the video file in Kilobytes
        LocalDateTime uploadDate // Timestamp of when the video was uploaded
) {}
