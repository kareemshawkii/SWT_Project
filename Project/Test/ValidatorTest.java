import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    private Validator validator = new Validator();

    @Test
    public void testValidMovieTitle() {
        Movie movie = new Movie("ABC123", "The Matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);
        assertNull(validationMessage);
    }

    @Test
    public void testInvalidMovieTitle() {
        Movie movie = new Movie("ABC123", "the matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);
        assertNotNull(validationMessage);  // Movie title is invalid (not capitalized)
    }

    @Test
    public void testValidMovieId() {
        Movie movie = new Movie("ABC123", "The Matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);
        assertNull(validationMessage);
    }

    @Test
    public void testInvalidMovieId() {
        Movie movie = new Movie("AB123", "The Matrix", new String[]{"Action", "Sci-Fi"});
        String validationMessage = validator.validateMovie(movie);
        assertNotNull(validationMessage);  // Movie ID is invalid
    }

    @Test
    public void testValidUserName() {
        User user = new User("U001", "John Doe", new HashSet<>());
        String validationMessage = validator.validateUser(user);
        assertNull(validationMessage);  // Valid user
    }

    @Test
    public void testInvalidUserName() {
        User user = new User("U001", "John123", new HashSet<>());
        String validationMessage = validator.validateUser(user);
        assertNotNull(validationMessage);  // Invalid user name (contains digits)
    }

    @Test
    public void testValidUserId() {
        User user = new User("12345678A", "John Doe", new HashSet<>());
        String validationMessage = validator.validateUser(user);
        assertNull(validationMessage);  // Valid user ID
    }

    @Test
    public void testInvalidUserId() {
        User user = new User("12345", "John Doe", new HashSet<>());
        String validationMessage = validator.validateUser(user);
        assertNotNull(validationMessage);  // Invalid user ID (too short)
    }
}
