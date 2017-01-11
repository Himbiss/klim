package de.himbiss.klim.fileservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vincent on 17.12.2016.
 */
public class FileService {
    private static final FileService instance = new FileService();
    private final Path filesDir = Paths.get(System.getProperty("user.home"), "klim_upload");

    public static FileService getInstance() {
        return instance;
    }

    private FileService() {
    }

    public byte[] getFile(int downloadId) throws IOException {
        Path file = filesDir.resolve(Integer.toString(downloadId) + ".png");
        return Files.readAllBytes(file);
    }
}
