package com.example.Karanis.impl;

import com.example.Karanis.DTO.VideoMetadataDTO;
import com.example.Karanis.entities.Video;
import com.example.Karanis.repository.VidRepository;
import com.example.Karanis.services.VidService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VidServiceImpl implements VidService {

    private final VidRepository vidRepository;

    @Override
    public void saveVideo(MultipartFile file, String name) throws IOException {
        // We bypass the standard save(entity) method and use a manual native query
        // to force correct parameter order and types.

        vidRepository.insertVideoNative(
                name,
                file.getContentType(),
                file.getSize() / 1024, // Calculate size in KB
                LocalDateTime.now(),   // Manually set the upload timestamp
                file.getBytes()        // The actual video data (byte array)
        );
    }

    @Override
    public List<VideoMetadataDTO> findAllMetadata() {
        // This method calls the *other* custom query in the repository which is fine
        return vidRepository.findAllMetadataDTOs();
    }

    @Override
    @Transactional(readOnly = true)
    public Video getVideoById(Long id) {
        return vidRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteVideo(Long id) {
        Optional<Video> videoOptional = vidRepository.findById(id);
        if (videoOptional.isPresent()) {
            vidRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


