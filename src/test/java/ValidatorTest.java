import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidateMovieTitle() { //lessa
        assertTrue(Validator.validateTitle("The Matrix"));
        assertFalse(Validator.validateTitle("The Matrix "));
        assertFalse(Validator.validateTitle("the matrix"));
        assertFalse(Validator.validateTitle("The matrix"));
        assertFalse(Validator.validateTitle("10 Days To Lose A Man"));
        assertFalse(Validator.validateTitle("500 days of summer"));
        assertFalse(Validator.validateTitle("#1 Cheerleader Camp"));
        assertFalse(Validator.validateTitle(" Harry Potter"));             //error --> fixed
        assertFalse(Validator.validateTitle(" "));

    }

    @Test
    public void testValidateMovieId() { //lessa
        //movie objects     List<Movie> movieList;
        //add movie object to list
        //add dup movie with same ID as before
        //call isUnique
        //return false if dup
//        assertTrue(Validator.validateMovieId("TM123", "The Matrix", existingIds));
//        existingIds.add("TM123");
//       // assertFalse(Validator.validateMovieId("TM123", "The Matrix", existingIds));
//        assertTrue(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
//        assertTrue(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
//        existingIds.add("TGW100");
//        existingIds.add("TGW100");
//        assertFalse(Validator.validateMovieId("TGW100", "The Good Wife", existingIds));
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
        assertFalse(Validator.validateUserName("123456"));

    }

    @Test
    public void testValidateUserId() {
        HashSet<String> existingIds = new HashSet<>();
        existingIds.add("123456789");
        existingIds.add("12345679A");
        assertFalse(Validator.validateUserId("123456789B", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("12345", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("123456789", existingIds)); //ERROR      --> not accepted
        assertFalse(Validator.validateUserId("123456789A", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("123456789", existingIds)); // existing id
        assertTrue(Validator.validateUserId("12345678A", existingIds)); //true
        assertFalse(Validator.validateUserId("12345678", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("12345679A", existingIds)); // not unique

    }
    //branch coverage====================kareem=======================================================
    @Test
    void testValidateTitle_Valid() {
        assertTrue(Validator.validateTitle("The Matrix"));
    }

    @Test
    void testValidateTitle_InvalidLeadingOrTrailingSpace() {
        assertFalse(Validator.validateTitle(" The Matrix"));
        assertFalse(Validator.validateTitle("The Matrix "));
    }

    @Test
    void testValidateTitle_InvalidLowercaseStart() {
        assertFalse(Validator.validateTitle("the Matrix"));
    }

    @Test
    void testValidateMovieId_Valid() {
        Movie movie = new Movie("The Matrix", "TM123", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertTrue(Validator.validateMovieId("TM123", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_WrongPrefix() {
        Movie movie = new Movie("The Matrix", "MX123", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertFalse(Validator.validateMovieId("MX123", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_InvalidDigits() {
        Movie movie = new Movie("The Matrix", "TM12", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertFalse(Validator.validateMovieId("TM12", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_DuplicateNumbers() {
        Movie m1 = new Movie("Test Movie", "TM123", List.of("Genre"));
        Movie m2 = new Movie("Test Movie", "TM123", List.of("Genre"));
        List<Movie> movies = List.of(m1, m2);
        assertFalse(Validator.validateMovieId("TM123", "Test Movie", movies));
    }

    @Test
    void testValidateUserName_Valid() {
        assertTrue(Validator.validateUserName("John Doe"));
    }

    @Test
    void testValidateUserName_LeadingSpace() {
        assertFalse(Validator.validateUserName(" John"));
    }

    @Test
    void testValidateUserName_InvalidCharacters() {
        assertFalse(Validator.validateUserName("John123"));
    }

    @Test
    void testValidateUserId_Valid() {
        Set<String> existing = new HashSet<>();
        assertTrue(Validator.validateUserId("12345678X", existing));
    }

    @Test
    void testValidateUserId_InvalidFormat() {
        Set<String> existing = new HashSet<>();
        assertFalse(Validator.validateUserId("1234X", existing));
    }

    @Test
    void testValidateUserId_DuplicateId() {
        Set<String> existing = new HashSet<>(Set.of("12345678X"));
        assertFalse(Validator.validateUserId("12345678X", existing));
    }
}
