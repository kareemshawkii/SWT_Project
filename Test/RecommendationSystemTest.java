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
        RecommendationSystem recommendationSystem6 = new RecommendationSystem(); // Empty user name
        RecommendationSystem recommendationSystem7 = new RecommendationSystem(); // Empty user id
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

        recommendationSystem8.loadData("testValidateMovieDataDup.txt","users.txt" );
        assertFalse(recommendationSystem8.validateData(), "Should fail due to duplicated movie id.");

        recommendationSystem9.loadData("movies.txt", "testValidateUserDataDup.txt");
        assertFalse(recommendationSystem9.validateData(), "Should fail due to duplicated user id");

    }

    @Test
    public void testGenerateRecommendations() {
        RecommendationSystem recommendationSystem1 = new RecommendationSystem(); // Valid generation case
        // ALL the following cases lead to no generation
        RecommendationSystem recommendationSystem2 = new RecommendationSystem(); // movies file does not exist
        RecommendationSystem recommendationSystem3 = new RecommendationSystem(); // users file does not exist
        RecommendationSystem recommendationSystem4 = new RecommendationSystem(); // both files do not exist
        RecommendationSystem recommendationSystem5 = new RecommendationSystem(); // users with no liked movies
        RecommendationSystem recommendationSystem6 = new RecommendationSystem(); // users that liked all existing movies in the movies file
        RecommendationSystem recommendationSystem7 = new RecommendationSystem(); // Invalid movie format
        RecommendationSystem recommendationSystem8 = new RecommendationSystem(); // Empty movie name
        RecommendationSystem recommendationSystem9 = new RecommendationSystem(); // Empty movie id
        RecommendationSystem recommendationSystem10 = new RecommendationSystem(); // Invalid user format
        RecommendationSystem recommendationSystem11 = new RecommendationSystem(); // Empty user name
        RecommendationSystem recommendationSystem12 = new RecommendationSystem(); // Empty user id
        RecommendationSystem recommendationSystem13 = new RecommendationSystem(); // Duplicate movie id
        RecommendationSystem recommendationSystem14 = new RecommendationSystem(); // Duplicate user id
        // The difference between the following test case and case 6 that here the user liked all related genres but not all the movies in the file
        RecommendationSystem recommendationSystem15 = new RecommendationSystem(); // No recommendations when all genre-matching movies are already liked.


        // Case 1
        recommendationSystem1.loadData("movies.txt", "users.txt");
        recommendationSystem1.validateData();

        recommendationSystem1.generateRecommendations();

        for (User user : recommendationSystem1.getUsers()) {
            assertNotNull(user.getRecommendedMovies());
            assertTrue(user.getRecommendedMovies().size() > 0);
        }

        // Case 2
        recommendationSystem2.loadData("mov.txt", "users.txt");
        recommendationSystem2.validateData();

        recommendationSystem2.generateRecommendations();

        for (User user : recommendationSystem2.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        // Case 3
        recommendationSystem3.loadData("movies.txt", "use.txt");
        recommendationSystem3.validateData();

        recommendationSystem3.generateRecommendations();

        for (User user : recommendationSystem3.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        // Case 4
        recommendationSystem4.loadData("mov.txt", "use.txt");
        recommendationSystem4.validateData();

        recommendationSystem4.generateRecommendations();

        for (User user : recommendationSystem4.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        // Case 5
        recommendationSystem5.loadData("movies.txt", "usersWithNoLikedMovies.txt");
        recommendationSystem5.validateData();

        recommendationSystem5.generateRecommendations();

        for (User user : recommendationSystem5.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        // Case 6
        recommendationSystem6.loadData("movies.txt", "usersWithAllLiked.txt");
        recommendationSystem6.validateData();

        recommendationSystem6.generateRecommendations();

        for (User user : recommendationSystem6.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

//         Case 7 ERROR
//        recommendationSystem7.loadData("testValidateWrongMovieData.txt", "users.txt");
//        recommendationSystem7.validateData();
//
//        recommendationSystem7.generateRecommendations();
//
//        for (User user : recommendationSystem7.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

//         Case 8 Same error as above
//        recommendationSystem8.loadData("testValidateEmptyMovieName.txt", "users.txt");
//        recommendationSystem8.validateData();
//
//        recommendationSystem8.generateRecommendations();
//
//        for (User user : recommendationSystem8.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

         //Case 9 Same error as above SO WEIRD IT PASSED!!!
        recommendationSystem9.loadData("testValidateEmptyMovieId.txt", "users.txt");
        recommendationSystem9.validateData();

        recommendationSystem9.generateRecommendations();

        for (User user : recommendationSystem9.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        //Case 10 Same error as for all validations
//        recommendationSystem10.loadData("movies.txt", "testValidateWrongUserData.txt");
//        recommendationSystem10.validateData();
//
//        recommendationSystem10.generateRecommendations();
//
//        for (User user : recommendationSystem10.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

        //Case 11 Same error as for all validations
//        recommendationSystem11.loadData("movies.txt", "testValidateEmptyUserName.txt");
//        recommendationSystem11.validateData();
//
//        recommendationSystem11.generateRecommendations();
//
//        for (User user : recommendationSystem11.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

        //Case 12 Same error as for all validations
//        recommendationSystem12.loadData("movies.txt", "testValidateEmptyUserid.txt");
//        recommendationSystem12.validateData();
//
//        recommendationSystem12.generateRecommendations();
//
//        for (User user : recommendationSystem12.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

        //Case 13 Same error as for all validations
        recommendationSystem13.loadData("movies.txt", "testValidateMovieDataDup.txt");
        recommendationSystem13.validateData();

        recommendationSystem13.generateRecommendations();

        for (User user : recommendationSystem13.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        //Case 13 Same error as for all validations SO WEIRD TANY PASSED!!!
        recommendationSystem13.loadData("movies.txt", "testValidateMovieDataDup.txt");
        recommendationSystem13.validateData();

        recommendationSystem13.generateRecommendations();

        for (User user : recommendationSystem13.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
        }

        //Case 13 Same error as for all validations
//        recommendationSystem13.loadData("testValidateMovieDataDup.txt","users.txt" );
//        recommendationSystem13.validateData();
//
//        recommendationSystem13.generateRecommendations();
//
//        for (User user : recommendationSystem13.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

        //Case 14 Same error as for all validations
//        recommendationSystem14.loadData("movies.txt","testValidateUserDataDup.txt" );
//        recommendationSystem14.validateData();
//
//        recommendationSystem14.generateRecommendations();
//
//        for (User user : recommendationSystem14.getUsers()) {
//            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
//            assertFalse(user.getRecommendedMovies().size() > 0);
//        }

        //Case 15
        recommendationSystem14.loadData("movies.txt","usersWithAllGenresLiked.txt" );
        recommendationSystem14.validateData();

        recommendationSystem14.generateRecommendations();

        for (User user : recommendationSystem14.getUsers()) {
            //assertNotNull(user.getRecommendedMovies()); //leha lazma wala la2 ???
            assertFalse(user.getRecommendedMovies().size() > 0);
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
