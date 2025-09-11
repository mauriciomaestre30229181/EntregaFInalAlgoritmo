package JavaRace.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final String DEFAULT_STORAGE_PATH = System.getProperty("user.dir") + File.separator + "JavaRace" + File.separator + "storage";

    public static String getStoragePath() {
        return DEFAULT_STORAGE_PATH;
    }

    public static void ensureStorageDirectoryExists() throws IOException {
        Path path = Paths.get(DEFAULT_STORAGE_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directorio de almacenamiento creado en: " + path.toAbsolutePath());
        }

    }
}