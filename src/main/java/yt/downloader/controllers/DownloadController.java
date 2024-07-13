package yt.downloader.controllers;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yt.downloader.models.VideoInfo;
import yt.downloader.services.YouTubeService;


@Controller
public class DownloadController {

    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/fetchInfo")
    public String fetchInfo(@RequestParam String videoUrl, Model model) {
        if (videoUrl == null || videoUrl.isEmpty()) {
            model.addAttribute("error", "Video URL is required");
            return "index";
        }
        try {
            VideoInfo videoInfo = youTubeService.fetchVideoInfo(videoUrl);
            model.addAttribute("videoInfo", videoInfo);
            model.addAttribute("videoUrl", videoUrl);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch video information: " + e.getMessage());
        }
        return "index";
    }

    @PostMapping("/download")
    public String download(@RequestParam String videoUrl, @RequestParam String formatId, Model model) {
        if (videoUrl == null || videoUrl.isEmpty()) {
            model.addAttribute("error", "Video URL is required");
            return "index";
        }
        try {
            String fileName = youTubeService.downloadVideo(videoUrl, formatId);
            model.addAttribute("message", "Downloaded successfully: " + fileName);
            model.addAttribute("VideoName", fileName);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to download video: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("File name is required");
        }
        // Load file as Resource
        Resource resource = loadFileAsResource(fileName);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/downloadPage/{fileName}")
    public String downloadDlFile(@PathVariable String fileName,Model model) {
    
        if(fileName == null || fileName.isEmpty()) {
            model.addAttribute("error", "File name is required");
            return "index";
        }
        model.addAttribute("VideoName", fileName);
        return "download";
    }

    public Resource loadFileAsResource(String fileName) {
    try {
        Path filePath = Paths.get("videos/", fileName);
        Resource resource = new UrlResource(filePath.toUri());
        
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file: " + fileName);
        }
    } catch (IOException e) {
        throw new RuntimeException("Could not load the file: " + fileName, e);
    }
}
}