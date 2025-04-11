import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private FileHandler fileHandler = new FileHandler();

    @Test
    public void testReadMovies() throws IOException {
        List<Movie> movies = fileHandler.readMovies("movies.txt");
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testReadUsers() throws IOException {
        List<User> users = fileHandler.readUsers("users.txt");
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testWriteToFile() throws IOException {
        String output = "User1, U001, Movie1, Movie2";
        fileHandler.writeToFile("Recommendations.txt", output);

        try (BufferedReader reader = new BufferedReader(new FileReader("Recommendations.txt"))) {
            String line = reader.readLine();
            assertEquals(output, line);
        }
    }
}
