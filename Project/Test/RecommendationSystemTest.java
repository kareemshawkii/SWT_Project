import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;


public class RecommendationSystemTest {

    @Test
    public void testLoadData() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        recommendationSystem.loadData("test_movies.txt", "test_users.txt");

        assertFalse(recommendationSystem.getMovies().isEmpty());
        assertFalse(recommendationSystem.getUsers().isEmpty());
    }

    @Test
    public void testValidateData() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        recommendationSystem.loadData("test_movies.txt", "test_users.txt");

        assertTrue(recommendationSystem.validateData());
    }

    @Test
    public void testGenerateRecommendations() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        recommendationSystem.loadData("test_movies.txt", "test_users.txt");
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
        recommendationSystem.loadData("test_movies.txt", "test_users.txt");
        recommendationSystem.validateData();
        recommendationSystem.generateRecommendations();

        recommendationSystem.writeRecommendations("recommendations.txt");

        File file = new File("recommendations.txt");
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
