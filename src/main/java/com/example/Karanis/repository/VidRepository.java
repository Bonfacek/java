package com.example.Karanis.repository;

import com.example.Karanis.DTO.VideoMetadataDTO;
import com.example.Karanis.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; // Import this

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VidRepository extends JpaRepository<Video, Long> {

    @Query("SELECT new com.example.Karanis.DTO.VideoMetadataDTO(v.id, v.name, v.mimeType, v.videoFileSizeKB, v.uploadDate) FROM Video v")
    List<VideoMetadataDTO> findAllMetadataDTOs();

    // --- New Native Query Method for Uploading ---
    @Modifying
    @Transactional // Required for INSERT/UPDATE operations
    @Query(value = "INSERT INTO videos (name, mime_type, file_size_kb, upload_date, data) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void insertVideoNative(String name, String mimeType, Long fileSizeKB, LocalDateTime uploadDate, byte[] data);
}
