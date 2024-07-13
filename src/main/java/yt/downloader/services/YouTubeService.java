package yt.downloader.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import yt.downloader.models.VideoInfo;
import yt.downloader.utils.CommandUtil;

@Service
public class YouTubeService {


    public VideoInfo fetchVideoInfo(String videoUrl) throws Exception {
        String command = String.format("yt-dlp -j %s", videoUrl);
        String output = CommandUtil.executeCommand(command);
        JSONObject json = new JSONObject(output);
    
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(json.getString("title"));
        videoInfo.setThumbnailUrl(json.getString("thumbnail"));
    
        // Additional fields you might fetch
        videoInfo.setDuration(json.optInt("duration", 0)); // Duration in seconds
        videoInfo.setResolution(json.optString("resolution", "N/A"));
    
        List<VideoInfo.Format> formats = new ArrayList<>();
        JSONArray formatsArray = json.getJSONArray("formats");
        for (int i = 0; i < formatsArray.length(); i++) {
            JSONObject formatJson = formatsArray.getJSONObject(i);
            VideoInfo.Format format = new VideoInfo.Format();
            format.setFormatId(formatJson.getString("format_id"));
            format.setFormatNote(formatJson.optString("format_note", "N/A"));
            format.setExtension(formatJson.getString("ext"));
            formats.add(format);
        }
        videoInfo.setFormats(formats);
    
        return videoInfo;
    }
    
    // public VideoInfo fetchVideoInfo(String videoUrl) throws Exception {
    //     String command = String.format("yt-dlp -j %s", videoUrl);
    //     String output = CommandUtil.executeCommand(command);
    //     JSONObject json = new JSONObject(output);

    //     VideoInfo videoInfo = new VideoInfo();
    //     videoInfo.setTitle(json.getString("title"));
    //     videoInfo.setThumbnailUrl(json.getString("thumbnail"));

    //     List<VideoInfo.Format> formats = new ArrayList<>();
    //     JSONArray formatsArray = json.getJSONArray("formats");
    //     for (int i = 0; i < formatsArray.length(); i++) {
    //         JSONObject formatJson = formatsArray.getJSONObject(i);
    //         VideoInfo.Format format = new VideoInfo.Format();
    //         format.setFormatId(formatJson.getString("format_id"));
    //         format.setFormatNote(formatJson.optString("format_note", "N/A"));
    //         format.setExtension(formatJson.getString("ext"));
    //         formats.add(format);
    //     }
    //     videoInfo.setFormats(formats);

    //     return videoInfo;
    // }

    public String downloadVideo(String videoUrl, String formatId) throws Exception {
        String extension = ".mp4";
        if ("webm".equalsIgnoreCase(formatId)) {
            extension = ".webm";
        } else if ("webp".equalsIgnoreCase(formatId)) {
            extension = ".webp";
        }
        String fileName = fileNameGenerator() + formatId + extension;
        String filePath = "videos/" + fileName;
        String command = String.format("yt-dlp -f %s -o %s %s", formatId, filePath, videoUrl);
        CommandUtil.executeCommand(command);
        return fileName;
    }
    

    // public String downloadVideo(String videoUrl, String formatId) throws Exception {
    //     String fileName = fileNameGenerator() + formatId + ".mp4";
    //     String filePath = "videos/" + fileName;
    //     String command = String.format("yt-dlp -f %s -o %s %s", formatId, filePath, videoUrl);
    //     CommandUtil.executeCommand(command);
    //     return fileName;
    // }

    public static String fileNameGenerator() {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + ".";
        return fileName;
    }
}
