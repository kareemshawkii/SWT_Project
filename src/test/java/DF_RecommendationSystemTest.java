import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DF_RecommendationSystemTest {
    private RecommendationSystem recommendationSystem;
    private String moviesFile = "test_movies.txt";
    private String usersFile = "test_users.txt";
    private String outputFile = "test_recommendations.txt";

    @BeforeEach
    void setUp() throws Exception {
        recommendationSystem = new RecommendationSystem();

        List<String> movieData = Arrays.asList(
                "The Lion King,TLK001",
                "Animation,Adventure,Family",
                "Inception,I011",
                "Action,Sci-Fi,Thriller"
        );

        List<String> userData = Arrays.asList(
                "John Doe,12345678A",
                "TLK001",
                "Jane Smith,87654321B",
                "I011"
        );

        Files.write(new File(moviesFile).toPath(), movieData);
        Files.write(new File(usersFile).toPath(), userData);
    }

    @Test
    void testLoadDataValidFiles() {
        recommendationSystem.loadData(moviesFile, usersFile);
        assertEquals(2, recommendationSystem.getMovies().size(), "Should load 2 movies");
        assertEquals(2, recommendationSystem.getUsers().size(), "Should load 2 users");
        assertTrue(recommendationSystem.getErrors().isEmpty(), "No errors should occur");
    }

    @Test
    void testMovieMissingId() throws Exception {
        List<String> badMovieData = List.of(
                "The Lion King,",
                "Animation,Adventure,Family"
        );
        Files.write(new File(moviesFile).toPath(), badMovieData);
        recommendationSystem.loadData(moviesFile, usersFile);

        assertFalse(recommendationSystem.validateData(), "Movie with missing ID should fail validation");
    }

    @Test
    void testUserFavoriteMovieMissing() throws Exception {
        List<String> userData = List.of(
                "John Doe,12345678A",
                "NONEXISTENT" // ID that doesn't exist
        );
        Files.write(new File(usersFile).toPath(), userData);

        recommendationSystem.loadData(moviesFile, usersFile);

        assertFalse(recommendationSystem.validateData(), "User with nonexistent favorite movie should fail validation");
    }

    @Test
    void testValidateDataValid() {
        recommendationSystem.loadData(moviesFile, usersFile);
        assertTrue(recommendationSystem.validateData(), "Data should be valid");
    }

    @Test
    void testValidateDataInvalidUserName() throws Exception {
        List<String> badUserData = List.of(
                "john doe,12345678A", // name not capitalized
                "TK001"
        );
        Files.write(new File(usersFile).toPath(), badUserData);

        recommendationSystem.loadData(moviesFile, usersFile);
        assertFalse(recommendationSystem.validateData(), "Invalid user name should fail validation");
    }

    @Test
    void testGenerateRecommendations() {
        recommendationSystem.loadData(moviesFile, usersFile);
        recommendationSystem.generateRecommendations();

        List<User> users = recommendationSystem.getUsers();
        assertNotNull(users.get(0).getRecommendedMovies(), "Recommendations should not be null");
    }

    @Test
    void testWriteRecommendations() throws Exception {
        recommendationSystem.loadData(moviesFile, usersFile);
        recommendationSystem.generateRecommendations();
        recommendationSystem.writeRecommendations(outputFile);

        File outFile = new File(outputFile);
        assertTrue(outFile.exists(), "Output file should exist");

        List<String> outputContent = Files.readAllLines(outFile.toPath());
        assertFalse(outputContent.isEmpty(), "Output file should not be empty");
    }

    @Test
    void testDuplicateMovieIDs() throws Exception {
        List<String> movieData = Arrays.asList(
                "Inception,I001",
                "Action,Sci-Fi",
                "Inception Again,IA001",
                "Thriller"
        );
        Files.write(new File(moviesFile).toPath(), movieData);

        recommendationSystem.loadData(moviesFile, usersFile);

        assertFalse(recommendationSystem.validateData(), "Duplicate movie IDs should fail validation");
    }

}


