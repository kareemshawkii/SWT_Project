import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ValidatorTest {

    @Test
    public void testValidateMovieTitle() {
        assertTrue(Validator.validateTitle("The Matrix"));
        assertFalse(Validator.validateTitle("the matrix"));
        assertFalse(Validator.validateTitle("The matrix"));
    }

    @Test
    public void testValidateMovieId() {
        HashSet<String> existingIds = new HashSet<>();
        existingIds.add("THE001");
        assertTrue(Validator.validateMovieId("THE123", "The Matrix", existingIds));
        assertFalse(Validator.validateMovieId("THE123", "The Matrix", existingIds)); // Duplicate ID
    }

    @Test
    public void testValidateUserName() {
        assertTrue(Validator.validateUserName("John Doe"));
        assertFalse(Validator.validateUserName("John123"));
        assertFalse(Validator.validateUserName(" John"));
    }

    @Test
    public void testValidateUserId() {
        HashSet<String> existingIds = new HashSet<>();
        existingIds.add("123456789A");
        assertTrue(Validator.validateUserId("123456789B", existingIds));
        assertFalse(Validator.validateUserId("12345", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("123456789", existingIds)); // Invalid format
    }
}
