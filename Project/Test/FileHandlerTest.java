import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

public class FileHandlerTest {

    @Test
    public void testReadFile() {
        FileHandler fileHandler = new FileHandler();
        List<String> lines = fileHandler.readFile("test_movies.txt");
        assertNotNull(lines);
        assertTrue(lines.size() > 0);
    }

    @Test
    public void testWriteFile() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.writeFile("test_output.txt", List.of("Movie1", "Movie2", "Movie3"));

        File file = new File("test_output.txt");
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
