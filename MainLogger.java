package studentlogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MainLogger {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path file = Path.of("student_scores.csv");

        if (Files.exists(file)) Files.delete(file);

        SafeFileWriter writer = new SafeFileWriter(file);

        StudentRecord r1 = new StudentRecord("Aarav", "101", 92);
        StudentRecord r2 = new StudentRecord("Diya", "102", 88);
        StudentRecord r3 = new StudentRecord("Kiran", "103", 79);
        StudentRecord r4 = new StudentRecord("Megha", "104", 85);

        ScoreWriterTask task1 = new ScoreWriterTask(Arrays.asList(r1, r2), writer, 40);
        ScoreWriterTask task2 = new ScoreWriterTask(Arrays.asList(r3, r4), writer, 25);

        Thread t1 = new Thread(task1, "Thread-A");
        Thread t2 = new Thread(task2, "Thread-B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("All student submissions completed. File contents:");
        Files.lines(file).forEach(System.out::println);
    }
}
