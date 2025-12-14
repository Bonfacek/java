package com.example.Karanis.services;

import com.example.Karanis.DTO.VideoMetadataDTO;
import com.example.Karanis.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VidService {
    void saveVideo(MultipartFile file, String name) throws IOException;
    List<VideoMetadataDTO> findAllMetadata();
    Video getVideoById(Long id);
    boolean deleteVideo(Long id);
}
