import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileHandlerTest {
    private FileHandler fileHandler;
    private static final String TEST_FILE_PATH = "testOutput.txt";
    private static final String INVALID_PATH = "/invalid_dir/test.txt";
    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
    }

    @AfterEach
    void tearDown() {
        fileHandler = null;
        new File("Project/testOutput.txt").delete();
    }

    @Test
    void readUsersFileTest() {
        List<String> expected = Arrays.asList("Hassan Ali, 12345678X", "TSR001, TDK003", "Ali Mohamed, 87654321W", "TG002");
        assertEquals(expected, fileHandler.readFile("D://Documents//GitHub//SWT_Project//users.txt"));
    }

    @Test
    void readRecommendationsFileTest() {
        List<String> expected = Arrays.asList("Hassan Ali,12345678X", "The Dark Knight,The Godfather", "Ali Mohamed,87654321W", "The Dark Knight");
        assertEquals(expected, fileHandler.readFile("D://Documents//GitHub//SWT_Project//recommendations.txt"));
    }

    @Test
    void readMoviesFileTest() {
        List<String> expected = Arrays.asList("The Shawshank Redemption, TSR001", "Drama", "The Godfather, TG002", "Crime, Drama", "The Dark Knight, TDK003", "Action, Crime, Drama");
        assertEquals(expected, fileHandler.readFile("D://Documents//GitHub//SWT_Project//movies.txt/"));
    }


    @Test
    void writeFileTest() {
        // Step 1: Define the path and test content
        String testFilePath = "testOutput.txt";
        List<String> linesToWrite = Arrays.asList(
                "Line 1: Hello, world!",
                "Line 2: Testing file writing",
                "Line 3: End of file"
        );

        // Step 2: Write to the file
        fileHandler.writeFile(testFilePath, linesToWrite);

        // Step 3: Read back from the file
        List<String> readBack = fileHandler.readFile(testFilePath);

        // Step 4: Assert that both are equal
        assertEquals(linesToWrite, readBack);


    }
    //branch ==================================kareem============================================
    @Test
    void testReadFileWithContent() throws IOException {
        List<String> content = Arrays.asList(" Line1 ", "Line2", "Line3");
        Files.write(Paths.get(TEST_FILE_PATH), content);

        List<String> result = fileHandler.readFile(TEST_FILE_PATH);

        assertEquals(3, result.size());
        assertEquals("Line1", result.get(0));
    }

    @Test
    void testReadFileWithInvalidPath() {
        List<String> result = fileHandler.readFile("non_existing_file.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    void testWriteFileSuccessfully() throws IOException {
        List<String> content = Arrays.asList("Hello", "World");
        fileHandler.writeFile(TEST_FILE_PATH, content);

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_PATH));
        assertEquals(content, lines);
    }

    @Test
    void testWriteFileWithInvalidPath() {
        List<String> content = Arrays.asList("test");
        // This path may not work on all systems, adjust if needed
        fileHandler.writeFile(INVALID_PATH, content);
    }

}
