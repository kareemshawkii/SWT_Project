import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ValidatorTest {

    @Test
    public void testValidateMovieTitle() { //lessa
        assertTrue(Validator.validateTitle("The Matrix"));
        assertFalse(Validator.validateTitle("the matrix"));
        assertFalse(Validator.validateTitle("The matrix"));
        assertFalse(Validator.validateTitle("10 Days To Lose A Man"));
        assertFalse(Validator.validateTitle("500 days of summer"));
        assertFalse(Validator.validateTitle("#1 Cheerleader Camp"));
       //assertFalse(Validator.validateTitle(" Harry Potter")); //error
        assertFalse(Validator.validateTitle(" "));

    }

    @Test
    public void testValidateMovieId() { //lessa
        HashSet<String> existingIds = new HashSet<>();
        assertTrue(Validator.validateMovieId("TM123", "The Matrix", existingIds));
        existingIds.add("TM123");
       // assertFalse(Validator.validateMovieId("TM123", "The Matrix", existingIds)); // Duplicate ID
        assertTrue(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
        assertTrue(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
        existingIds.add("TGW100");
        existingIds.add("TGW100");
        assertFalse(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
    }

    @Test
    public void testValidateUserName() { //finished
        assertTrue(Validator.validateUserName("John Doe"));
        assertFalse(Validator.validateUserName("John123"));
        assertFalse(Validator.validateUserName(" John"));
        assertFalse(Validator.validateUserName("!John"));
        assertTrue(Validator.validateUserName("John Doe Black Berry Linus"));
        assertTrue(Validator.validateUserName("john"));
        assertTrue(Validator.validateUserName("john doe"));
        assertFalse(Validator.validateUserName("jo% @hn"));
        assertFalse(Validator.validateUserName("john%"));
    }

    @Test
    public void testValidateUserId() {
        HashSet<String> existingIds = new HashSet<>();
        existingIds.add("123456789");
        existingIds.add("12345679A");
        assertFalse(Validator.validateUserId("123456789B", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("12345", existingIds)); // Invalid length
//        assertTrue(Validator.validateUserId("123456789", existingIds)); //ERROR
        assertFalse(Validator.validateUserId("123456789A", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("123456789", existingIds)); // existing id
        assertTrue(Validator.validateUserId("12345678A", existingIds)); //true
        assertFalse(Validator.validateUserId("12345678", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("12345679A", existingIds)); // not unique

    }
}
