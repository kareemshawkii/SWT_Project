import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.io.*;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
class FileHandlerTest {
    private FileHandler fileHandler;
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

}
