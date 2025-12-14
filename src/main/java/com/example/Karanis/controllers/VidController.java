package com.example.Karanis.controllers;


import com.example.Karanis.DTO.VideoMetadataDTO;
import com.example.Karanis.entities.Video;
import com.example.Karanis.services.VidService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@AllArgsConstructor
public class VidController {

    private final VidService vidService;

    // --- 1. Upload Video (POST) ---
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a video file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            vidService.saveVideo(file, name);
            return new ResponseEntity<>(
                    "Video uploaded successfully: " + file.getOriginalFilename(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Could not upload the file: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 2. Get All Video Metadata (GET) ---
    @GetMapping // Maps to /api/videos
    public ResponseEntity<List<VideoMetadataDTO>> getAllVideosMetadata(){
        // Uses the imported VideoMetadataDTO
        List<VideoMetadataDTO> videos = vidService.findAllMetadata();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    // --- 3. Stream a Specific Video (GET) ---
    @GetMapping("/{id}/stream")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable Long id,
            @RequestHeader(value = "Range", required = false) String rangeHeader
    ) {

        Video video = vidService.getVideoById(id);
        if (video == null || video.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = video.getData();
        long fileSize = data.length;

        long start = 0;
        long end = fileSize - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                end = Long.parseLong(ranges[1]);
            }
        }

        if (end >= fileSize) {
            end = fileSize - 1;
        }

        byte[] chunk = Arrays.copyOfRange(data, (int) start, (int) end + 1);

        String mimeType = video.getMimeType();
        if (mimeType == null || mimeType.isBlank()) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.set(HttpHeaders.CONTENT_RANGE,
                "bytes " + start + "-" + end + "/" + fileSize);
        headers.setContentLength(chunk.length);

        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(new ByteArrayResource(chunk));
    }


    // --- 4. Delete a Video (DELETE) ---
    @DeleteMapping("/{id}") // Maps to /api/videos/{id}
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        boolean deleted = vidService.deleteVideo(id);

        if (deleted) {
            return new ResponseEntity<>("Video deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Video not found.", HttpStatus.NOT_FOUND);
        }
    }
}
