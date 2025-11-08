package studentlogger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SafeFileWriter {
    private final Path targetFile;
    private final Object fileLock = new Object();

    public SafeFileWriter(Path targetFile) {
        this.targetFile = targetFile;
    }

    public void writeEntry(String entry) throws IOException {
        synchronized (fileLock) {
            try (BufferedWriter bw = Files.newBufferedWriter(
                    targetFile,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                bw.write(entry);
                bw.newLine();
            }
        }
    }
}
