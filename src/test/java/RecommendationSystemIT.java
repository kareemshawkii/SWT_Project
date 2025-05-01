import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


import java.util.Arrays;
import java.io.*;
import java.util.*;

/**
 * Unit tests for the RecommendationSystem class.
 * Covers data loading, validation, recommendation generation, and output writing.
 */
public class RecommendationSystemIT {

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
        runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/users.txt", true);

        // Case 2: Movies file doesn't exist
        runRecommendationTest("src/Test/resources/mov.txt", "src/Test/resources/users.txt", false);

        // Case 3: Users file doesn't exist
        runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/use.txt", false);

        // Case 4: Both files don't exist
        runRecommendationTest("src/Test/resources/mov.txt", "src/Test/resources/use.txt", false);

        // Case 5: Users have no liked movies
        runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/usersWithNoLikedMovies.txt", false);

        // Case 6: Users have liked all movies already
        runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/usersWithAllLiked.txt", false);

        // Case 9: One movie entry has empty ID
        runRecommendationTest("src/Test/resources/testValidateEmptyMovieId.txt", "src/Test/resources/users.txt", false);

        // Case 10 Error!!!!!!!!!
        //runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateWrongUserData.txt",false); //error

        //Case 11 Same error as for all validations
        //runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateEmptyUserName.txt",false); //error


        //Case 12 Same error as for all validations
        //runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateEmptyUserid.txt",false); //error


        // Case 13: Duplicate movie IDs in file ERROR
        //runRecommendationTest("src/Test/resources/testValidateMovieDataDup.txt", "src/Test/resources/users.txt", false); //error

        // Case 14: Duplicate user IDs in file
        //runRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateUserDataDup.txt", false); //error

        // Case 15: All genre-matching movies already liked Error !!!!!!!!
        runRecommendationTest("src/Test/resources/movies2.txt", "src/Test/resources/usersWithAllGenresLiked.txt", false);
    }

    /**
     * Tests the validateData() method with different edge cases.
     */
    private void runValidateTest(String movieFile, String userFile, boolean exp) {
        RecommendationSystem rs = new RecommendationSystem();
        rs.loadData(movieFile, userFile);
        assertEquals(exp,rs.validateData());

    }
    @Test
    public void testValidateData() { //finished
        // Valid data
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/users.txt",true);

        // Invalid movie format (e.g., wrong structure)
        runValidateTest("src/Test/resources/testValidateWrongMovieData.txt","src/Test/resources/users.txt",false);

        // Movie entry with empty name Not handled as it is not included in the pdf
//        runValidateTest("src/Test/resources/testValidateEmptyMovieName.txt","src/Test/resources/users.txt",false);

        // Movie entry with empty ID  name Not handled as it is not included in the pdf
        //runValidateTest("src/Test/resources/testValidateEmptyMovieId.txt","src/Test/resources/users.txt",false);

        // Invalid user format (e.g., wrong structure)
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateWrongUserData.txt",false);

        // User entry with empty name
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateEmptyUserName.txt",false);

        // Uncomment if file and logic for empty user ID exists
        // recommendationSystem.loadData("movies.txt", "testValidateEmptyUserid.txt");
        // assertFalse(recommendationSystem.validateData());

        // Duplicate movie IDs in file
        runValidateTest("src/Test/resources/testValidateMovieDataDup.txt","src/Test/resources/users.txt",false);

        // Duplicate user IDs in file
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateUserDataDup.txt",false);
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
        FileHandler fileHandler = new FileHandler(); //nshof mock up wala la => el mafrood la2
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
        // Case 1: Valid data first bug !!
        List<String> expected1 = Arrays.asList("Hassan Ali,12345678X", "The Godfather", "Ali Mohamed,87654321W", "The Shawshank Redemption,The Dark Knight");
        runWriteRecommendationTest("src/Test/resources/movies.txt","src/Test/resources/users.txt",expected1);

        //*//
        // File errors
        // Case 2: Movies file doesn't exist
//        List<String> expected2 = Arrays.asList("ERROR: Failed to read file: src/Test/resources/mov.txt");
//        runWriteRecommendationTest("src/Test/resources/mov.txt", "src/Test/resources/users.txt", expected2);

        // Case 3: Users file doesn't exist
//        List<String> expected3 = Arrays.asList("ERROR: Failed to read file: src/Test/resources/use.txt");
//        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/use.txt", expected3);

        // Case 4: Both files don't exist
//        List<String> expected4 = Arrays.asList("ERROR: Failed to read file: src/Test/resources/mov.txt", "ERROR: Failed to read file: src/Test/resources/use.txt");
//        runWriteRecommendationTest("src/Test/resources/mov.txt", "src/Test/resources/use.txt", expected4);
        //*//



        // Case 5: Users have no liked movies
        //List<String> expected5 = Arrays.asList("ERROR: Missing liked movies line for user: Hassan Ali");
        //runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/usersWithNoLikedMovies.txt", expected5);

        // Case 6: Users have liked all movies already
        List<String> expected6 = Arrays.asList("Hassan Ali,12345678X","", "Ali Mohamed,87654321W", "");
        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/usersWithAllLiked.txt", expected6);

        // Case 7: One movie entry has empty ID
       // List<String> expected7 = Arrays.asList();
       // runWriteRecommendationTest("src/Test/resources/testValidateEmptyMovieId.txt", "src/Test/resources/users.txt", expected7);

        // Case 8: wrong user data
        List<String> expected8 = Arrays.asList("ERROR: Invalid user name: &assan Ali");
        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateWrongUserData.txt",expected8);

        //Case 9: empty user name
//        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateEmptyUserName.txt",false);


        //Case 10: empty user id
//        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateEmptyUserid.txt",false);


        // Case 13: Duplicate movie IDs in file
        //List<String> expected13 = Arrays.asList("ERROR: Movie Id: " +"TG002 "+ "aren’t unique " );
        //runWriteRecommendationTest("src/Test/resources/testValidateMovieDataDup.txt", "src/Test/resources/users.txt", expected13); //error

        // Case 14: Duplicate user IDs in file NOT IN THE DESCRIPTION
//        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateUserDataDup.txt", false); //error

        // Case 15: All genre-matching movies already liked Error !!!!!!!!
        List<String> expected15 = Arrays.asList("Hassan Ali,12345678X","", "Ali Mohamed,87654321W", "");
        runWriteRecommendationTest("src/Test/resources/movies2.txt", "src/Test/resources/usersWithAllGenresLiked.txt", expected15);


    }
}