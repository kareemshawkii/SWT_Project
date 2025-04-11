import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationSystemTest {

    private FileHandler fileHandler;
    private Validator validator;
    private RecommendationSystem recommendationSystem;

    @BeforeEach
    public void setUp() {
        fileHandler = new FileHandler();
        validator = new Validator();
        recommendationSystem = new RecommendationSystem();
    }

    @Test
    public void testGenerateRecommendations() {
        // Setup a test user and movies
        User user = new User("U001", "John Doe", new HashSet<>(Set.of("M001", "M002")));
        Set<String> recommendedMovies = recommendationSystem.generateRecommendations(user);

        assertNotNull(recommendedMovies);
        assertTrue(recommendedMovies.contains("Movie 3"));
    }

    @Test
    public void testMainWithValidData() throws IOException {
        // Given: Movies and Users file with valid content
        String[] args = {"movies.txt", "users.txt"};
        RecommendationSystem.main(args);  // Check if it executes without errors
        // Optionally, check file output
    }

    @Test
    public void testMovieValidation() {
        Movie movie = new Movie("ABC123", "The Matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);

        assertNull(validationMessage);  // No error expected for valid movie
    }

    @Test
    public void testInvalidMovieValidation() {
        Movie movie = new Movie("AB123", "Matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);

        assertNotNull(validationMessage);  // Movie ID should be invalid
    }

    @Test
    public void testUserValidation() {
        User user = new User("U001", "John Doe", new HashSet<>());
        String validationMessage = validator.validateUser(user);

        assertNull(validationMessage);  // No error expected for valid user
    }

    @Test
    public void testInvalidUserValidation() {
        User user = new User("12345", "John", new HashSet<>());
        String validationMessage = validator.validateUser(user);

        assertNotNull(validationMessage);  // User ID should be invalid
    }
}
