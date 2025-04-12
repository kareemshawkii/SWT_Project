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
    private void runRecommendationUnitTest(List<Movie> movies, List<User> users, boolean expectRecommendations) {
        RecommendationSystem rs = new RecommendationSystem();
        rs.setMovies(movies);
        rs.setUsers(users);
        rs.generateRecommendations();

        for (User user : rs.getUsers()) {
            if (expectRecommendations) {
                assertNotNull(user.getRecommendedMovies());
                assertTrue(user.getRecommendedMovies().size() > 0);
            } else {
                assertTrue(user.getRecommendedMovies() == null || user.getRecommendedMovies().isEmpty());
            }
        }
    }


    /**
     * Tests recommendation generation under various conditions:
     * - Valid input
     * - Missing files
     * - Edge cases like users with no preferences
     * - Invalid or duplicate data
     */
    @Test
    public void testGenerateRecommendations() {
        List<String> genres1 = Arrays.asList("Sci-Fi","Action");
        List<String> genres2 = Arrays.asList("Sci-Fi", "Drama");
        List<String> genres3 = Arrays.asList("Crime", "Drama");
        List<String> genres4 = Arrays.asList("Comedy");
        List<String> genres5 = Arrays.asList("Horror", "Thriller");

        Movie m1 = new Movie("Inception", "I123", genres1);
        Movie m2 = new Movie("Interstellar", "I234", genres2);
        Movie m3 = new Movie("The Godfather", "TG001", genres3);
        Movie m4 = new Movie("The Hangover", "TH111", genres4);
        Movie m5 = new Movie("The Conjuring", "TC999", genres5);

        List<Movie> movieList = Arrays.asList(m1, m2, m3, m4, m5);

        // Case 1: User likes Sci-Fi movie (should get "The Godfather" as a rec)
        User u1 = new User("John", "12345678X", Arrays.asList("I123"));

        // Case 2: User likes all available movies (should get no recommendations)
        User u2 = new User("Ali", "87654321A", Arrays.asList("I123", "I234", "TG001", "TH111", "TC999"));

        // Case 3: User likes no movies (should get no recommendations)
        User u3 = new User("Nada", "123456789", new ArrayList<>());

        // Case 4: User likes only Drama (should get Sci-Fi/Action movie)
        User u4 = new User("Mona", "098765432", Arrays.asList("TG001"));

        // Case 5: User likes Horror (should get something else)
        User u5 = new User("Tamer", "135790246", Arrays.asList("TC999"));

        // Test case 1: One recommendation expected
        runRecommendationUnitTest(movieList, Arrays.asList(u1), true);

        // Test case 2: No recommendations (liked everything)
        runRecommendationUnitTest(movieList, Arrays.asList(u2), false);

        // Test case 3: No recommendations (liked nothing)
        runRecommendationUnitTest(movieList, Arrays.asList(u3), false);

        // Test case 4: Should get Sci-Fi/Action (e.g., Inception)
        runRecommendationUnitTest(movieList, Arrays.asList(u4), true);

        // Test case 5: Should get something that's not Horror/Thriller
        runRecommendationUnitTest(movieList, Arrays.asList(u5), true);
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

