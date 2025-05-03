import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
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
    private static final String TEST_READ_FILE = "test_read.txt";
    private static final String TEST_WRITE_FILE = "test_write.txt";
    @BeforeEach
    void setUp() throws IOException {
        fileHandler = new FileHandler();
        Files.write(Paths.get(TEST_READ_FILE), Arrays.asList(" Line1 ", " Line2 "));
    }

    @AfterEach
    void tearDown() {
        fileHandler = null;
        new File("Project/testOutput.txt").delete();
        try {
            Files.deleteIfExists(Paths.get(TEST_READ_FILE));
            Files.deleteIfExists(Paths.get(TEST_WRITE_FILE));
        } catch (IOException ignored) {}
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

    //============================Ahmed - Statement============================//
    @Test
    public void testReadFile_ValidFile() {
        List<String> lines = fileHandler.readFile(TEST_READ_FILE);
        assertEquals(Arrays.asList("Line1", "Line2"), lines);
    }

    @Test
    public void testReadFile_FileNotFound() {
        List<String> lines = fileHandler.readFile("nonexistent_file.txt");
        assertTrue(lines.isEmpty());
    }

    @Test
    public void testWriteFile_ValidFile() throws IOException {
        List<String> content = Arrays.asList("Hello", "World");
        fileHandler.writeFile(TEST_WRITE_FILE, content);

        List<String> linesWritten = Files.readAllLines(Paths.get(TEST_WRITE_FILE));
        assertEquals(content, linesWritten);
    }

    @Test
    public void testWriteFile_InvalidPath() {
        // Use an invalid file path (simulate failure, OS-dependent)
        String invalidPath = "?:/invalid<>path.txt";  // Invalid on Windows
        List<String> content = Arrays.asList("Test");

        // Should not throw, just print error
        assertDoesNotThrow(() -> fileHandler.writeFile(invalidPath, content));
    }
    //condition testing=========================kareem=======================================
    @Test
    void testReadFileWithContentCon() throws IOException {
        Files.write(Paths.get(TEST_READ_FILE), List.of(" Hello ", "World "));
        List<String> lines = fileHandler.readFile(TEST_READ_FILE);
        assertEquals(List.of("Hello", "World"), lines); // C1: true
    }

    @Test
    void testReadFileEmpty() throws IOException {
        Files.write(Paths.get(TEST_READ_FILE), List.of());
        List<String> lines = fileHandler.readFile(TEST_READ_FILE);
        assertTrue(lines.isEmpty()); // C1: false
    }

    @Test
    void testReadFileIOException() {
        List<String> lines = fileHandler.readFile("nonexistent_file.txt");
        assertTrue(lines.isEmpty()); // catch triggered
    }

    @Test
    void testWriteFileSuccess() throws IOException {
        List<String> content = List.of("Line1", "Line2");
        fileHandler.writeFile(TEST_WRITE_FILE, content);
        List<String> readBack = Files.readAllLines(Paths.get(TEST_WRITE_FILE));
        assertEquals(content, readBack); // no catch
    }

    @Test
    void testWriteFileIOException() {
        List<String> content = List.of("fail");
        fileHandler.writeFile(INVALID_PATH, content); // catch triggered
    }
    //=======================Ahmed - path=========================//

    @Test
    void testReadFile_ValidFilee() throws IOException {
        // Create a temporary file and write content
        File tempFile = File.createTempFile("testFile", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("Line 1\nLine 2\nLine 3");
        writer.close();

        List<String> lines = fileHandler.readFile(tempFile.getAbsolutePath());

        // Check if the content is correctly read
        assertEquals(3, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("Line 3", lines.get(2));

        // Clean up
        tempFile.delete();
    }

    @Test
    void testReadFile_InvalidFile() {
        // Try to read a non-existent file
        List<String> lines = fileHandler.readFile("invalidFilePath.txt");

        // Should return an empty list and print an error
        assertTrue(lines.isEmpty());
    }

    @Test
    void testWriteFile_ValidContent() throws IOException {
        // Create a temporary file
        File tempFile = File.createTempFile("testWrite", ".txt");

        List<String> content = List.of("Hello", "World");
        fileHandler.writeFile(tempFile.getAbsolutePath(), content);

        // Verify content was written correctly
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertEquals("Hello", reader.readLine().trim());
        assertEquals("World", reader.readLine().trim());
        reader.close();

        // Clean up
        tempFile.delete();
    }

    @Test
    void testWriteFile_InvalidPatht() {
        // Try writing to an invalid path
        List<String> content = List.of("Invalid Path Test");
        fileHandler.writeFile("/invalid/path/test.txt", content);

        // Should print an error (no assertion required for console output)
    }

    @Test
    void testReadFile_EmptyFile() throws IOException {
        // Create an empty temporary file
        File tempFile = File.createTempFile("emptyFile", ".txt");

        List<String> lines = fileHandler.readFile(tempFile.getAbsolutePath());

        // Should return an empty list
        assertTrue(lines.isEmpty());

        // Clean up
        tempFile.delete();
    }

    @Test
    void testWriteFile_EmptyContent() throws IOException {
        // Create a temporary file
        File tempFile = File.createTempFile("emptyContentFile", ".txt");

        List<String> content = List.of();
        fileHandler.writeFile(tempFile.getAbsolutePath(), content);

        // Verify file is created but empty
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertNull(reader.readLine());
        reader.close();

        // Clean up
        tempFile.delete();
    }

    @Test
    void testReadFile_FileWithSpaces() throws IOException {
        // Create a temporary file with spaces
        File tempFile = File.createTempFile("fileWithSpaces", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("  Line 1 with spaces  \n  Line 2  \n  ");
        writer.close();

        List<String> lines = fileHandler.readFile(tempFile.getAbsolutePath());

        // Check that the spaces are trimmed
        assertEquals(3, lines.size());
        assertEquals("Line 1 with spaces", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("", lines.get(2));

        // Clean up
        tempFile.delete();
    }
}
