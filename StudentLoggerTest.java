package studentlogger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StudentLoggerTest {
    private final Path testFile = Path.of("test_output.csv");

    @AfterEach
    public void clearFile() throws IOException {
        if (Files.exists(testFile)) Files.delete(testFile);
    }

    @Test
    public void verifyThreadSafeFileWriting() throws IOException, InterruptedException {
        SafeFileWriter writer = new SafeFileWriter(testFile);

        StudentRecord s1 = new StudentRecord("Alpha", "R01", 78);
        StudentRecord s2 = new StudentRecord("Beta", "R02", 81);
        StudentRecord s3 = new StudentRecord("Gamma", "R03", 67);
        StudentRecord s4 = new StudentRecord("Delta", "R04", 89);

        ScoreWriterTask t1 = new ScoreWriterTask(Arrays.asList(s1, s2), writer, 15);
        ScoreWriterTask t2 = new ScoreWriterTask(Arrays.asList(s3, s4), writer, 10);

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(4, lines.size());
        assertTrue(lines.contains(s1.toCsvFormat()));
        assertTrue(lines.contains(s2.toCsvFormat()));
        assertTrue(lines.contains(s3.toCsvFormat()));
        assertTrue(lines.contains(s4.toCsvFormat()));
    }
}
