import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.Arrays;
import java.io.*;
import java.util.*;


public class RecommendationSystemIT {

    private RecommendationSystem recommendationSystem;


     // Initializes a RecommendationSystem instance before each test.

    @BeforeEach
    void setUp() {

        recommendationSystem = new RecommendationSystem();

    }

     // Cleans up the RecommendationSystem instance after each test.

    @AfterEach
    void tearDown() throws IOException {
        recommendationSystem = null;
        // Optionally delete the output file after tests
        // new File("Project/testOutput.txt").delete();
    }

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

        // Case 7: One movie entry has empty ID
        runRecommendationTest("src/Test/resources/testValidateEmptyMovieId.txt", "src/Test/resources/users.txt", false);

        // Case 8: All genre-matching movies already liked
        runRecommendationTest("src/Test/resources/movies2.txt", "src/Test/resources/usersWithAllGenresLiked.txt", false);
    }


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

        // Invalid user format (e.g., wrong structure)
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateWrongUserData.txt",false);

        // User entry with empty name
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateEmptyUserName.txt",false);

        // Duplicate movie IDs in file
        runValidateTest("src/Test/resources/testValidateMovieDataDup.txt","src/Test/resources/users.txt",false);

        // Duplicate user IDs in file
        runValidateTest("src/Test/resources/movies.txt","src/Test/resources/testValidateUserDataDup.txt",false);
    }

    private void runWriteRecommendationTest(String movieFile, String userFile, List<String> expectedOutput) {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        FileHandler fileHandler = new FileHandler();
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
        // Case 1: Valid data
        List<String> expected1 = Arrays.asList("Hassan Ali,12345678X", "The Godfather", "Ali Mohamed,87654321W", "The Shawshank Redemption,The Dark Knight");
        runWriteRecommendationTest("src/Test/resources/movies.txt","src/Test/resources/users.txt",expected1);

        // Case 2: Users have liked all movies already
        List<String> expected2 = Arrays.asList("Hassan Ali,12345678X","", "Ali Mohamed,87654321W", "");
        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/usersWithAllLiked.txt", expected2);

        // Case 3: One movie entry has empty ID
        List<String> expected3 = Arrays.asList("ERROR: Invalid movie line format at line 3: The Godfather,");
        runWriteRecommendationTest("src/Test/resources/testValidateEmptyMovieId.txt", "src/Test/resources/users.txt", expected3);

        // Case 4: wrong user data
        List<String> expected4 = Arrays.asList("ERROR: Invalid user name: &assan Ali");
        runWriteRecommendationTest("src/Test/resources/movies.txt", "src/Test/resources/testValidateWrongUserData.txt",expected4);


        // Case 5: All genre-matching movies already liked
        List<String> expected5 = Arrays.asList("Hassan Ali,12345678X","", "Ali Mohamed,87654321W", "");
        runWriteRecommendationTest("src/Test/resources/movies2.txt", "src/Test/resources/usersWithAllGenresLiked.txt", expected5);

    }

}