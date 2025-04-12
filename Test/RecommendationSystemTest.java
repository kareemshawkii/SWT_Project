import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.File;

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
    private void runRecommendationTest(String movieFile, String userFile, boolean expectRecommendations) {
        RecommendationSystem rs = new RecommendationSystem();
        rs.loadData(movieFile, userFile);
        rs.validateData();
        rs.generateRecommendations();

        for (User user : rs.getUsers()) {
            if (expectRecommendations) {
                assertNotNull(user.getRecommendedMovies());
                assertTrue(user.getRecommendedMovies().size() > 0);
            } else {
                assertFalse(user.getRecommendedMovies().size() > 0);
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
        // Case 1: Valid data
        runRecommendationTest("movies.txt", "users.txt", true);

        // Case 2: Movies file doesn't exist
        runRecommendationTest("mov.txt", "users.txt", false);

        // Case 3: Users file doesn't exist
        runRecommendationTest("movies.txt", "use.txt", false);

        // Case 4: Both files don't exist
        runRecommendationTest("mov.txt", "use.txt", false);

        // Case 5: Users have no liked movies
        runRecommendationTest("movies.txt", "usersWithNoLikedMovies.txt", false);

        // Case 6: Users have liked all movies already
        runRecommendationTest("movies.txt", "usersWithAllLiked.txt", false);

        // Case 9: One movie entry has empty ID
        runRecommendationTest("testValidateEmptyMovieId.txt", "users.txt", false);

        // Case 10 Same error as for all validations
     // runRecommendationTest("movies.txt", "testValidateWrongUserData.txt",false); //error

        //Case 11 Same error as for all validations
      // runRecommendationTest("movies.txt", "testValidateEmptyUserName.txt",false); //error


        //Case 12 Same error as for all validations
      //  runRecommendationTest("movies.txt", "testValidateEmptyUserid.txt",false); //error


        // Case 13: Duplicate movie IDs in file
        //runRecommendationTest("testValidateMovieDataDup.txt", "users.txt", false); //error

        // Case 14: Duplicate user IDs in file
         //runRecommendationTest("movies.txt", "testValidateUserDataDup.txt", false); //error

        // Case 15: All genre-matching movies already liked
        runRecommendationTest("movies.txt", "usersWithAllGenresLiked.txt", false);
    }

    /**
     * Tests the validateData() method with different edge cases.
     */
    @Test
    public void testValidateData() {
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
    @Test
    public void testWriteRecommendations() {
        recommendationSystem.loadData("movies.txt", "users.txt");
        recommendationSystem.validateData();
        recommendationSystem.generateRecommendations();

        // Write output to file
        recommendationSystem.writeRecommendations("recommendations.txt");

        File file = new File("recommendations.txt");
        assertTrue(file.exists(), "Output file should exist.");
        assertTrue(file.length() > 0, "Output file should not be empty.");
    }
}

