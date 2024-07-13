package yt.downloader.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandUtil {

    // public static String executeCommand(String command) throws Exception {
    //     Process process = Runtime.getRuntime().exec(command);
    //     StringBuilder output;
    //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    //         output = new StringBuilder();
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             output.append(line).append("\n");
    //         }
    //     }
    //     int exitCode = process.waitFor();
    //     if (exitCode != 0) {
    //         throw new RuntimeException("Command failed with exit code " + exitCode);
    //     }
    //     return output.toString();
    // }

    public static String executeCommand(String command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code " + exitCode);
        }

        return output.toString();
    }
}