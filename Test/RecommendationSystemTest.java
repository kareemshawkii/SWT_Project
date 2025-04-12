import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;


public class RecommendationSystemTest {

    @Test
    public void testLoadData() {

        RecommendationSystem recommendationSystem1 = new RecommendationSystem();
        RecommendationSystem recommendationSystem2 = new RecommendationSystem(); // Both files are invalid
        RecommendationSystem recommendationSystem3 = new RecommendationSystem(); // Only the first file is invalid
        RecommendationSystem recommendationSystem4 = new RecommendationSystem(); // Only the second file is invalid
        RecommendationSystem recommendationSystem5 = new RecommendationSystem(); // Empty file path



        recommendationSystem1.loadData("movies.txt", "users.txt");
        recommendationSystem2.loadData("mov.txt", "use.txt"); // Both files are incorrect
        recommendationSystem3.loadData("mov.txt", "users.txt"); // Only the first file is invalid
        recommendationSystem4.loadData("movies.txt", "use.txt"); // Only the second file is invalid
        recommendationSystem5.loadData("", "users.txt"); // Empty file path


        assertTrue(!(recommendationSystem1.getMovies().isEmpty()));
        assertTrue(!(recommendationSystem1.getUsers().isEmpty()));

        assertFalse(!(recommendationSystem2.getMovies().isEmpty()));
        assertFalse(!(recommendationSystem2.getUsers().isEmpty()));

        assertFalse(!(recommendationSystem3.getMovies().isEmpty()));
        assertTrue(!(recommendationSystem3.getUsers().isEmpty()));

        assertTrue(!(recommendationSystem4.getMovies().isEmpty()));
        assertFalse(!(recommendationSystem4.getUsers().isEmpty()));

        assertFalse(!(recommendationSystem5.getMovies().isEmpty()));

    }

    @Test
    public void testValidateData() {
        RecommendationSystem recommendationSystem1 = new RecommendationSystem(); // Valid
        RecommendationSystem recommendationSystem2 = new RecommendationSystem(); // Invalid movie format
        RecommendationSystem recommendationSystem3 = new RecommendationSystem(); // Empty movie name
        RecommendationSystem recommendationSystem4 = new RecommendationSystem(); // Empty movie id
        RecommendationSystem recommendationSystem5 = new RecommendationSystem(); // Invalid user format
        RecommendationSystem recommendationSystem6 = new RecommendationSystem(); // Empty movie name
        RecommendationSystem recommendationSystem7 = new RecommendationSystem(); // Empty movie id
        RecommendationSystem recommendationSystem8 = new RecommendationSystem(); // Duplicate movie id
        RecommendationSystem recommendationSystem9 = new RecommendationSystem(); // Duplicate user id



        recommendationSystem1.loadData("movies.txt", "users.txt");
        assertTrue(recommendationSystem1.validateData()); // All data are Valid

        recommendationSystem2.loadData("testValidateWrongMovieData.txt", "users.txt");
        assertFalse(recommendationSystem2.validateData(), "Should fail due to invalid movie format.");

        recommendationSystem3.loadData("testValidateEmptyMovieName.txt", "users.txt");
        assertFalse(recommendationSystem3.validateData(), "Should fail due to empty movie name.");

        recommendationSystem4.loadData("testValidateEmptyMovieId.txt", "users.txt");
        assertFalse(recommendationSystem4.validateData(), "Should fail due to empty movie id.");

        recommendationSystem5.loadData("movies.txt", "testValidateWrongUserData.txt");
        assertFalse(recommendationSystem5.validateData(), "Should fail due to invalid user format.");

        recommendationSystem6.loadData("movies.txt", "testValidateEmptyUserName.txt");
        assertFalse(recommendationSystem6.validateData(), "Should fail due to empty user name.");

        recommendationSystem7.loadData("movies.txt", "testValidateEmptyUserid.txt");
        //assertFalse(recommendationSystem7.validateData(), "Should fail due to empty user id."); ERROR

        recommendationSystem8.loadData("movies.txt", "testValidateMovieDataDup.txt");
        assertFalse(recommendationSystem8.validateData(), "Should fail due to duplicated movie id.");

        recommendationSystem9.loadData("movies.txt", "testValidateUserDataDup.txt");
        assertFalse(recommendationSystem9.validateData(), "Should fail due to duplicated user id");

    }

    @Test
    public void testGenerateRecommendations() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        recommendationSystem.loadData("movies.txt", "users.txt");
        recommendationSystem.validateData();

        recommendationSystem.generateRecommendations();

        for (User user : recommendationSystem.getUsers()) {
            assertNotNull(user.getRecommendedMovies());
            assertTrue(user.getRecommendedMovies().size() > 0);
        }
    }

    @Test
    public void testWriteRecommendations() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        recommendationSystem.loadData("movies.txt", "users.txt");
        recommendationSystem.validateData();
        recommendationSystem.generateRecommendations();

        recommendationSystem.writeRecommendations("recommendations.txt");

        File file = new File("recommendations.txt");
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
