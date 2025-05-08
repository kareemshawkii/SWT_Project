import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidateMovieTitle() {
        assertTrue(Validator.validateTitle("The Matrix"));
        assertFalse(Validator.validateTitle("The Matrix "));
        assertFalse(Validator.validateTitle("the matrix"));
        assertFalse(Validator.validateTitle("The matrix"));
        assertFalse(Validator.validateTitle("10 Days To Lose A Man"));
        assertFalse(Validator.validateTitle("500 days of summer"));
        assertFalse(Validator.validateTitle("#1 Cheerleader Camp"));
        assertFalse(Validator.validateTitle(" Harry Potter"));
        assertFalse(Validator.validateTitle(" "));

    }

    @Test
    void validateMovieIdTestCases() {
        List<Movie> emptyList = new ArrayList<>();
        List<Movie> existingList = new ArrayList<>();
        existingList.add(new Movie("The Matrix", "TM123", Arrays.asList("Action")));

        // TC01: Valid ID
        assertTrue(Validator.validateMovieId("TSR123", "The Shawshank Redemption", emptyList));

        // TC02: Null ID
        assertFalse(Validator.validateMovieId(null, "Inception", emptyList));

        // TC03: Empty ID
        assertFalse(Validator.validateMovieId("", "Inception", emptyList));

        // TC04: Incorrect prefix
        assertFalse(Validator.validateMovieId("INC123", "The Matrix", emptyList));

        // TC05: Less than 3 digits
        assertFalse(Validator.validateMovieId("TM12", "The Matrix", emptyList));

        // TC06: More than 3 digits
        assertFalse(Validator.validateMovieId("TM1234", "The Matrix", emptyList));

        // TC07: Incorrect capital letters
        assertFalse(Validator.validateMovieId("MTR123", "The Matrix", emptyList));

    }

    @Test
    public void testValidateUserName() { //finished
        assertTrue(Validator.validateUserName("John Doe"));
        assertFalse(Validator.validateUserName("John123"));
        assertFalse(Validator.validateUserName(" John"));
        assertFalse(Validator.validateUserName("!John"));
        assertTrue(Validator.validateUserName("John Doe Black Berry Linus"));
        assertFalse(Validator.validateUserName("john"));
        assertFalse(Validator.validateUserName("john doe"));
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
    //branch============================kareem===============================================
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
    void testValidateMovieId_Validd() {
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
    void testValidateMovieId_InvalidDigitss() {
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
    //=======================Ahmed - Statement=========================//
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
        Movie movie = new Movie("The Matrix", "TM001", Arrays.asList("Action"));
        List<Movie> movies = Collections.singletonList(movie);
        assertFalse(Validator.validateMovieId("T001", "The Matrix", movies));
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
    public void testValidateUserName_Validd() {
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
    public void testValidateUserId_Validd() {
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
    //condition===================================kareem=======================================================
    @Test
    void testValidateTitle_ValidCond() {
        assertTrue(Validator.validateTitle("The Matrix"));
    }

    @Test
    void testValidateTitle_LeadingOrTrailingSpace() {
        assertFalse(Validator.validateTitle(" The Matrix"));
        assertFalse(Validator.validateTitle("The Matrix "));
    }

    @Test
    void testValidateTitle_LowercaseStartWord() {
        assertFalse(Validator.validateTitle("the Matrix"));
    }

    @Test
    void testValidateMovieId_ValidCond() {
        Movie movie = new Movie("The Matrix", "TM123", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertTrue(Validator.validateMovieId("TM123", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_WrongPrefixCond() {
        Movie movie = new Movie("The Matrix", "XX123", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertFalse(Validator.validateMovieId("XX123", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_InvalidDigitFormat() {
        Movie movie = new Movie("The Matrix", "TM12", List.of("Action"));
        List<Movie> movies = List.of(movie);
        assertFalse(Validator.validateMovieId("TM12", "The Matrix", movies));
    }

    @Test
    void testValidateMovieId_NotUniqueDigits() {
        Movie m1 = new Movie("Test Movie", "TM123", List.of("Action"));
        Movie m2 = new Movie("Test Movie", "TM123", List.of("Action"));
        List<Movie> movies = List.of(m1, m2);
        assertFalse(Validator.validateMovieId("TM123", "Test Movie", movies));
    }

    @Test
    void testValidateUserName_ValidCond() {
        assertTrue(Validator.validateUserName("John Doe"));
    }

    @Test
    void testValidateUserName_LeadingSpaceCond() {
        assertFalse(Validator.validateUserName(" John Doe"));
    }

    @Test
    void testValidateUserName_InvalidChars() {
        assertFalse(Validator.validateUserName("John123"));
    }

    @Test
    void testValidateUserId_ValidCond() {
        Set<String> ids = new HashSet<>();
        assertTrue(Validator.validateUserId("12345678X", ids));
    }

    @Test
    void testValidateUserId_InvalidLength() {
        Set<String> ids = new HashSet<>();
        assertFalse(Validator.validateUserId("1234X", ids));
    }

    @Test
    void testValidateUserId_InvalidPattern() {
        Set<String> ids = new HashSet<>();
        assertFalse(Validator.validateUserId("ABCDEFGHX", ids));
    }

    @Test
    void testValidateUserId_DuplicateIdCond() {
        Set<String> ids = new HashSet<>(Set.of("12345678X"));
        assertFalse(Validator.validateUserId("12345678X", ids));
    }
    //=======================Ahmed - path=========================//
    @Test void testValidateTitle_Validd() {
        assertTrue(Validator.validateTitle("The Matrix"));
    }

    @Test void testValidateTitle_LeadingSpace() {
        assertFalse(Validator.validateTitle(" The Matrix"));
    }

    @Test void testValidateTitle_TrailingSpace() {
        assertFalse(Validator.validateTitle("The Matrix "));
    }

    @Test void testValidateTitle_LowercaseWord() {
        assertFalse(Validator.validateTitle("the Matrix"));
    }

    @Test void testValidateMovieId_Validdd() {
        List<String> genres = List.of("Action", "Sci-Fi");
        List<Movie> movies = List.of(new Movie("The Matrix", "TM001", genres));
        assertTrue(Validator.validateMovieId("TM001", "The Matrix", movies));
    }

    @Test void testValidateMovieId_WrongPrefixx() {
        List<String> genres = List.of("Action", "Sci-Fi");
        List<Movie> movies = List.of(new Movie("The Matrix", "TM001", genres));
        assertFalse(Validator.validateMovieId("TX001", "The Matrix", movies));
    }

    @Test void testValidateMovieId_ShortDigits() {
        List<String> genres = List.of("Action", "Sci-Fi");
        List<Movie> movies = List.of(new Movie("The Matrix", "TM001", genres));
        assertFalse(Validator.validateMovieId("TM01", "The Matrix", movies));
    }

    @Test void testValidateMovieId_NotUnique() {
        List<String> genres = List.of("Action", "Sci-Fi");
        List<Movie> movies = List.of(
                new Movie("The Matrix", "TM001", genres),
                new Movie("The Matrix Reloaded", "TM001", genres)
        );
        assertFalse(Validator.validateMovieId("TM001", "The Matrix", movies));
    }

    @Test void testValidateUserName_Validdd() {
        assertTrue(Validator.validateUserName("Alice Smith"));
    }

    @Test void testValidateUserName_LeadingSpacee() {
        assertFalse(Validator.validateUserName(" alice"));
    }

    @Test void testValidateUserName_NoCapital() {
        assertFalse(Validator.validateUserName("alice"));
    }

    @Test void testValidateUserId_Validdd() {
        Set<String> ids = new HashSet<>();
        assertTrue(Validator.validateUserId("12345678A", ids));
    }

    @Test void testValidateUserId_Duplicate() {
        Set<String> ids = new HashSet<>(Set.of("12345678A"));
        assertFalse(Validator.validateUserId("12345678A", ids));
    }

    @Test void testValidateUserId_Short() {
        assertFalse(Validator.validateUserId("1234", new HashSet<>()));
    }

    @Test void testValidateUserId_InvalidFormatt() {
        assertFalse(Validator.validateUserId("ABCDEFGH1", new HashSet<>()));
    }
}
