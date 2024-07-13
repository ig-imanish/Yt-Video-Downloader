package yt.downloader.models;

import java.util.List;

public class VideoInfo {

    private String title;
    private String thumbnailUrl;
    private int duration; // in seconds
    private String resolution;
    private List<Format> formats;

    // Constructors, getters, and setters

    public static class Format {
        private String formatId;
        private String formatNote;
        private String extension;

        // Constructors, getters, and setters

        public String getFormatId() {
            return formatId;
        }

        public void setFormatId(String formatId) {
            this.formatId = formatId;
        }

        public String getFormatNote() {
            return formatNote;
        }

        public void setFormatNote(String formatNote) {
            this.formatNote = formatNote;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        @Override
        public String toString() {
            return "Format [formatId=" + formatId + ", formatNote=" + formatNote + ", extension=" + extension + "]";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    @Override
    public String toString() {
        return "VideoInfo [title=" + title + ", thumbnailUrl=" + thumbnailUrl + ", duration=" + duration
                + ", resolution=" + resolution + ", formats=" + formats + "]";
    }
}
