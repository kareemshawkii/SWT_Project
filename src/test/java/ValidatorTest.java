import org.junit.jupiter.api.Test;

import java.util.*;

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
        assertFalse(Validator.validateUserId("123456789", existingIds)); //ERROR--> not accepted
        assertFalse(Validator.validateUserId("123456789A", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("123456789", existingIds)); // existing id
        assertTrue(Validator.validateUserId("12345678A", existingIds)); //true
        assertFalse(Validator.validateUserId("12345678", existingIds)); // Invalid length
        assertFalse(Validator.validateUserId("12345679A", existingIds)); // not unique
    }
    @Test
    public void testValidateTitle_ValidTitle() {
        assertTrue(Validator.validateTitle("The Matrix"));
    }

    @Test
    public void testValidateTitle_InvalidStartOrEndSpace() {
        assertFalse(Validator.validateTitle(" The Matrix"));
        assertFalse(Validator.validateTitle("The Matrix "));
    }

    @Test
    public void testValidateTitle_WordNotCapitalized() {
        assertFalse(Validator.validateTitle("the Matrix"));
        assertFalse(Validator.validateTitle("The matrix"));
    }

    @Test
    public void testValidateMovieId_Valid() {
        Movie movie = new Movie("The Matrix", "TM001", Arrays.asList("Sci-Fi"));
        List<Movie> movies = Collections.singletonList(movie);
        assertTrue(Validator.validateMovieId("TM001", "The Matrix", movies));
    }

    @Test
    public void testValidateMovieId_InvalidCapitalPrefix() {
        Movie movie = new Movie("The Matrix", "TheMatrix001", Arrays.asList("Action"));
        List<Movie> movies = Collections.singletonList(movie);
        assertFalse(Validator.validateMovieId("TheMatrix001", "The Matrix", movies));
    }

    @Test
    public void testValidateMovieId_InvalidDigits() {
        Movie movie = new Movie("The Matrix", "TheMatrix01", Arrays.asList("Action"));
        List<Movie> movies = Collections.singletonList(movie);
        assertFalse(Validator.validateMovieId("TheMatrix01", "The Matrix", movies));
    }

    @Test
    public void testValidateMovieId_DuplicateIds() {
        Movie movie1 = new Movie("The Matrix", "TheMatrix001", Arrays.asList("Sci-Fi"));
        Movie movie2 = new Movie("The Matrix", "TheMatrix001", Arrays.asList("Action"));
        List<Movie> movies = Arrays.asList(movie1, movie2);
        assertFalse(Validator.validateMovieId("TheMatrix001", "The Matrix", movies));
    }

    @Test
    public void testValidateUserName_Valid() {
        assertTrue(Validator.validateUserName("John Doe"));
    }

    @Test
    public void testValidateUserName_InvalidLeadingSpace() {
        assertFalse(Validator.validateUserName(" John"));
    }

    @Test
    public void testValidateUserName_LowerCaseOnly() {
        assertFalse(Validator.validateUserName("john doe"));
    }

    @Test
    public void testValidateUserId_Valid() {
        Set<String> existingIds = new HashSet<>(Arrays.asList("11112222A", "87654321B"));
        assertTrue(Validator.validateUserId("12345678Z", existingIds));
    }

    @Test
    public void testValidateUserId_InvalidLengthOrFormat() {
        Set<String> existingIds = new HashSet<>();
        assertFalse(Validator.validateUserId("1234567", existingIds));       // too short
        assertFalse(Validator.validateUserId("abcdefgh", existingIds));      // not digits
        assertFalse(Validator.validateUserId("12345678#", existingIds));     // invalid suffix
    }

    @Test
    public void testValidateUserId_AlreadyExists() {
        Set<String> existingIds = new HashSet<>(Arrays.asList("12345678A"));
        assertFalse(Validator.validateUserId("12345678A", existingIds));
    }
}
