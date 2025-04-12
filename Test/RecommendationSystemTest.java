import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


import java.util.Arrays;
import java.io.*;
import java.util.*;

/**
 * Unit tests for the RecommendationSystem class.
 * Covers data loading, validation, recommendation generation, and output writing.
 */
public class RecommendationSystemTest {

    private RecommendationSystem recommendationSystem;

    /**
     * Initializes a fresh RecommendationSystem instance before each test.
     */
    @BeforeEach
    void setUp() {
        recommendationSystem = new RecommendationSystem();
    }

    /**
     * Cleans up the RecommendationSystem instance after each test.
     */
    @AfterEach
    void tearDown() {
        recommendationSystem = null;
        // Optionally delete the output file after tests
        // new File("Project/testOutput.txt").delete();
    }

    /**
     * Utility method to test the recommendation generation logic.
     * @param movieFile Path to the movies file.
     * @param userFile Path to the users file.
     * @param expectRecommendations Whether recommendations are expected.
     */


    /**
     * Tests recommendation generation under various conditions:
     * - Valid input
     * - Missing files
     * - Edge cases like users with no preferences
     * - Invalid or duplicate data
     */
    @Test
    public void testGenerateRecommendations() {
        List<String> genres1 = Arrays.asList("Sci-Fi", "Action");
        List<String> genres2 = Arrays.asList("Sci-Fi", "Drama");
        List<String> genres3 = Arrays.asList("Crime", "Drama");
        List<String> genres4 = Arrays.asList("Comedy");
        List<String> genres5 = Arrays.asList("Horror", "Thriller");
        List<String> genres6 = Arrays.asList("Action", "Crime", "Drama");
        List<String> genres7 = Arrays.asList("Drama", "Crime");

        Movie m1 = new Movie("Inception", "I123", genres1);
        Movie m2 = new Movie("Interstellar", "I234", genres2);
        Movie m3 = new Movie("The Godfather", "TG001", genres3);
        Movie m4 = new Movie("The Hangover", "TH111", genres4);
        Movie m5 = new Movie("The Conjuring", "TC999", genres5);
        Movie m6 = new Movie("The Dark Knight", "TDK003", genres6);
        Movie m7 = new Movie("The Shawshank Redemption", "TSR001", genres7);

        List<Movie> movieList = Arrays.asList(m1, m2, m3, m4, m5, m6, m7);

        User u1 = new User("John", "12345678X", Arrays.asList("I123"));  // Sci-Fi, Action
        User u2 = new User("Ali", "87654321A", Arrays.asList("I123", "I234", "TG001", "TH111", "TC999")); // All except m6, m7
        User u3 = new User("Nada", "123456789", new ArrayList<>()); // No likes
        User u4 = new User("Mona", "098765432", Arrays.asList("TG001")); // Drama, Crime
        User u5 = new User("Tamer", "135790246", Arrays.asList("TC999")); // Horror, Thriller
        User u6 = new User("Hassan Ali", "12345678X", Arrays.asList("TDK003", "TSR001")); // Action, Crime, Drama
        User u7 = new User("Ali Mohamed", "87654321W", Arrays.asList("TG001")); // Drama, Crime

        List<User> users = Arrays.asList(u1, u2, u3, u4, u5, u6, u7);

        RecommendationSystem rs = new RecommendationSystem();
        rs.setMovies(movieList);
        rs.setUsers(users);
        rs.generateRecommendations();

        // u1: Likes Inception (Sci-Fi, Action) -> Recommend Interstellar, Dark Knight
        assertTrue(u1.getRecommendedMovies().contains("Interstellar"));
        assertTrue(u1.getRecommendedMovies().contains("The Dark Knight"));
        assertEquals(2, u1.getRecommendedMovies().size());

        // u2: Likes all movies except m6, m7 => Genres: Sci-Fi, Action, Drama, Crime, Comedy, Horror, Thriller
        // m6 and m7 share genres, so recommend both
        assertTrue(u2.getRecommendedMovies().contains("The Dark Knight"));
        assertTrue(u2.getRecommendedMovies().contains("The Shawshank Redemption"));
        assertEquals(2, u2.getRecommendedMovies().size());

        // u3: Likes nothing -> Recommend nothing
        assertTrue(u3.getRecommendedMovies().isEmpty());

        // u4: Likes Godfather (Crime, Drama) -> Recommend Interstellar, Dark Knight, Shawshank
        assertTrue(u4.getRecommendedMovies().contains("Interstellar"));
        assertTrue(u4.getRecommendedMovies().contains("The Dark Knight"));
        assertTrue(u4.getRecommendedMovies().contains("The Shawshank Redemption"));
        assertEquals(3, u4.getRecommendedMovies().size());

        // u5: Likes The Conjuring (Horror, Thriller) -> No other movie shares these genres
        assertTrue(u5.getRecommendedMovies().isEmpty());

        // u6: Likes Dark Knight & Shawshank (Action, Crime, Drama) -> Recommend Godfather, Interstellar, Inception
        assertTrue(u6.getRecommendedMovies().contains("The Godfather"));
        assertTrue(u6.getRecommendedMovies().contains("Interstellar"));
        assertTrue(u6.getRecommendedMovies().contains("Inception"));
        assertEquals(3, u6.getRecommendedMovies().size());

        // u7: Likes Godfather (Drama, Crime) -> Recommend Interstellar, Dark Knight, Shawshank
        assertTrue(u7.getRecommendedMovies().contains("Interstellar"));
        assertTrue(u7.getRecommendedMovies().contains("The Dark Knight"));
        assertTrue(u7.getRecommendedMovies().contains("The Shawshank Redemption"));
        assertEquals(3, u7.getRecommendedMovies().size());
    }




    /**
     * Tests the validateData() method with different edge cases.
     */
    @Test
    public void testValidateData() { //finished
        // Valid data
        recommendationSystem.loadData("movies.txt", "users.txt");
        assertTrue(recommendationSystem.validateData());

        // Invalid movie format (e.g., missing fields or wrong structure)
        recommendationSystem.loadData("testValidateWrongMovieData.txt", "users.txt");
        assertFalse(recommendationSystem.validateData());

        // Movie entry with empty name
        recommendationSystem.loadData("testValidateEmptyMovieName.txt", "users.txt");
        assertFalse(recommendationSystem.validateData());

        // Movie entry with empty ID
        recommendationSystem.loadData("testValidateEmptyMovieId.txt", "users.txt");
        assertFalse(recommendationSystem.validateData());

        // Invalid user format (e.g., missing fields)
        recommendationSystem.loadData("movies.txt", "testValidateWrongUserData.txt");
        assertFalse(recommendationSystem.validateData());

        // User entry with empty name
        recommendationSystem.loadData("movies.txt", "testValidateEmptyUserName.txt");
        assertFalse(recommendationSystem.validateData());

        // Uncomment if file and logic for empty user ID exists
        // recommendationSystem.loadData("movies.txt", "testValidateEmptyUserid.txt");
        // assertFalse(recommendationSystem.validateData());

        // Duplicate movie IDs in file
        recommendationSystem.loadData("testValidateMovieDataDup.txt", "users.txt");
        assertFalse(recommendationSystem.validateData());

        // Duplicate user IDs in file
        recommendationSystem.loadData("movies.txt", "testValidateUserDataDup.txt");
        assertFalse(recommendationSystem.validateData());
    }

    /**
     * Tests whether the recommendations are correctly written to a file.
     * Verifies the output file exists and is not empty.
     */

    /**
     * Helper method to test writing recommendations and verifying the output.
     */
    private void runWriteRecommendationTest(String movieFile, String userFile, List<String> expectedOutput) {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        FileHandler fileHandler = new FileHandler(); //nshof mock up wala la
        String outputFile = "recommendations.txt";

        recommendationSystem.loadData(movieFile, userFile);
        recommendationSystem.validateData();
        recommendationSystem.generateRecommendations();
        recommendationSystem.writeRecommendations(outputFile);

        File file = new File(outputFile);
        assertTrue(file.exists(), "Output file should exist");
        if (expectedOutput.isEmpty()) {
            assertEquals(0, fileHandler.readFile(outputFile).size(), "Expected no recommendations");
        } else {
            assertEquals(expectedOutput, fileHandler.readFile(outputFile), "Output does not match expected recommendations");
        }
    }
    @Test
    public void testWriteRecommendations() {
        // Valid case
        runWriteRecommendationTest("movies.txt", "users.txt",
                Arrays.asList(
                        "Hassan Ali,12345678X",
                        "The Godfather",
                        "Ali Mohamed,87654321W",
                        "The Shawshank Redemption, The Dark Knight"
                ));  //error


    }
}

