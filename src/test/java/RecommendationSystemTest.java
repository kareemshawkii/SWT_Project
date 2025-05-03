import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RecommendationSystem class.
 * Covers data loading, validation, recommendation generation, and output writing.
 */
public class RecommendationSystemTest {

    private RecommendationSystem recommendationSystem;
    private RecommendationSystem rs;
    private static final String MOVIES_FILE = "src/Test/resources/movies.txt";
    private static final String USERS_FILE = "src/Test/resources/users.txt";
    private static final String OUTPUT_FILE = "recommendations.txt";

    private RecommendationSystem system;
    private final String testMovieFile = "src/Test/resources/movies.txt";
    private final String testUserFile = "src/Test/resources/users.txt";
    private final String testOutputFile = "src/Test/resources/test_output.txt";


    /**
     * Initializes a fresh RecommendationSystem instance before each test.
     */
    @BeforeEach
    void setUp() {
        recommendationSystem = new RecommendationSystem();
        rs = new RecommendationSystem();
        system = new RecommendationSystem();
    }

    /**
     * Cleans up the RecommendationSystem instance after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        recommendationSystem = null;
        // Optionally delete the output file after tests
        // new File("Project/testOutput.txt").delete();
        Files.deleteIfExists(Paths.get(MOVIES_FILE));
        Files.deleteIfExists(Paths.get(USERS_FILE));
        Files.deleteIfExists(Paths.get(OUTPUT_FILE));
        new File(testMovieFile).delete();
        new File(testUserFile).delete();
        new File(testOutputFile).delete();
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
        recommendationSystem.loadData("src/test/resources/movies.txt", "src/test/resources/users.txt");
        assertTrue(recommendationSystem.validateData());

        // Invalid movie format (e.g., missing fields or wrong structure)
        recommendationSystem.loadData("src/test/resources/testValidateWrongMovieData.txt", "src/test/resources/users.txt");
        assertFalse(recommendationSystem.validateData());

        // Movie entry with empty name
        recommendationSystem.loadData("src/test/resources/testValidateEmptyMovieName.txt", "src/test/resources/users.txt");
        assertFalse(recommendationSystem.validateData());

        // Movie entry with empty ID
        recommendationSystem.loadData("src/test/resources/testValidateEmptyMovieId.txt", "src/test/resources/users.txt");
        assertFalse(recommendationSystem.validateData());

        // Invalid user format (e.g., missing fields)
        recommendationSystem.loadData("src/test/resources/movies.txt", "src/test/resources/testValidateWrongUserData.txt");
        assertFalse(recommendationSystem.validateData());

        // User entry with empty name
        recommendationSystem.loadData("src/test/resources/movies.txt", "src/test/resources/testValidateEmptyUserName.txt");
        assertFalse(recommendationSystem.validateData());

        // Uncomment if file and logic for empty user ID exists
        // recommendationSystem.loadData("movies.txt", "testValidateEmptyUserid.txt");
        // assertFalse(recommendationSystem.validateData());

        // Duplicate movie IDs in file
        recommendationSystem.loadData("src/test/resources/testValidateMovieDataDup.txt", "src/test/resources/users.txt");
        assertFalse(recommendationSystem.validateData());

        // Duplicate user IDs in file
        recommendationSystem.loadData("src/test/resources/movies.txt", "src/test/resources/testValidateUserDataDup.txt");
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
        String outputFile = "src/test/resources/recommendations.txt";

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
        runWriteRecommendationTest("src/test/resources/movies.txt", "src/test/resources/users.txt",
                Arrays.asList(
                        "Hassan Ali,12345678X",
                        "The Godfather",
                        "Ali Mohamed,87654321W",
                        "The Shawshank Redemption,The Dark Knight"
                ));  //error


    }
    //branch test==========================kareem=================================================
    @Test
    void testLoadDataWithValidInputs() throws IOException {
        List<String> moviesData = Arrays.asList(
                "The Matrix,M1",
                "Action,Sci-Fi",
                "Titanic,M2",
                "Romance,Drama"
        );
        List<String> usersData = Arrays.asList(
                "Alice,123",
                "M1"
        );
        Files.write(Paths.get(MOVIES_FILE), moviesData);
        Files.write(Paths.get(USERS_FILE), usersData);

        rs.loadData(MOVIES_FILE, USERS_FILE);

        assertEquals(2, rs.getMovies().size());
        assertEquals(1, rs.getUsers().size());
    }

    @Test
    void testLoadDataWithMissingGenreLine() throws IOException {
        List<String> moviesData = Arrays.asList(
                "Movie1,M1"
        );
        Files.write(Paths.get(MOVIES_FILE), moviesData);
        Files.write(Paths.get(USERS_FILE), Collections.emptyList());

        rs.loadData(MOVIES_FILE, USERS_FILE);

        assertTrue(rs.getMovies().isEmpty());
    }

    @Test
    void testLoadDataWithInvalidUserLine() throws IOException {
        List<String> moviesData = Collections.emptyList();
        List<String> usersData = Arrays.asList(
                "InvalidUserLineWithoutComma"
        );
        Files.write(Paths.get(MOVIES_FILE), moviesData);
        Files.write(Paths.get(USERS_FILE), usersData);

        rs.loadData(MOVIES_FILE, USERS_FILE);
        assertEquals(0, rs.getUsers().size());
    }

    @Test
    void testValidateDataFailsOnDuplicateMovieId() {
        Movie m1 = new Movie("Movie1", "M1", Arrays.asList("Action"));
        Movie m2 = new Movie("Movie2", "M1", Arrays.asList("Drama"));
        rs.setMovies(Arrays.asList(m1, m2));
        rs.setUsers(Collections.emptyList());

        boolean valid = rs.validateData();
        assertFalse(valid);
    }

    @Test
    void testValidateDataFailsOnDuplicateUserId() {
        User u1 = new User("User1", "U1", List.of("M1"));
        User u2 = new User("User2", "U1", List.of("M2"));
        rs.setMovies(Collections.emptyList());
        rs.setUsers(Arrays.asList(u1, u2));

        boolean valid = rs.validateData();
        assertFalse(valid);
    }

    @Test
    void testGenerateRecommendationsProducesOutput() {
        Movie m1 = new Movie("ActionMovie", "A1", Arrays.asList("Action"));
        Movie m2 = new Movie("DramaMovie", "D1", Arrays.asList("Drama"));
        rs.setMovies(Arrays.asList(m1, m2));

        User user = new User("TestUser", "T1", List.of("A1"));
        rs.setUsers(List.of(user));

        rs.generateRecommendations();

        List<String> recommended = user.getRecommendedMovies();
        assertNotNull(recommended);
    }

    @Test
    void testGenerateRecommendationsWithEmptyLikes() {
        Movie m1 = new Movie("TestMovie", "M1", List.of("Comedy"));
        User user = new User("EmptyLiker", "U1", Collections.emptyList());
        rs.setMovies(List.of(m1));
        rs.setUsers(List.of(user));

        rs.generateRecommendations();
        assertTrue(user.getRecommendedMovies().isEmpty());
    }

    @Test
    void testWriteRecommendationsCreatesFile() throws IOException {
        Movie m1 = new Movie("TestMovie", "T1", List.of("Comedy"));
        User u1 = new User("TestUser", "U1", List.of("T1"));

        rs.setMovies(List.of(m1));
        rs.setUsers(List.of(u1));
        rs.generateRecommendations();
        rs.writeRecommendations(OUTPUT_FILE);

        assertTrue(Files.exists(Paths.get(OUTPUT_FILE)));
    }

    @Test
    void testWriteRecommendationsWhenValidationFails() throws IOException {
        Movie m1 = new Movie("BadMovie", "X", List.of(""));
        Movie m2 = new Movie("BadMovie", "X", List.of(""));
        rs.setMovies(List.of(m1, m2));
        rs.setUsers(Collections.emptyList());

        rs.writeRecommendations(OUTPUT_FILE);

        List<String> output = Files.readAllLines(Paths.get(OUTPUT_FILE));
        assertFalse(output.isEmpty());
        assertTrue(output.get(0).contains("ERROR"));
    }

    @Test
    void testLoadDataWithMixedValidAndInvalidLines() throws IOException {
        List<String> moviesData = Arrays.asList(
                "Movie1,M1",
                "Action",
                "Movie2,M2",
                "Drama",
                "InvalidLine"
        );
        List<String> usersData = Arrays.asList(
                "User1,U1",
                "M1",
                "User2,U2",
                "M2",
                "InvalidUserLine"
        );
        Files.write(Paths.get(MOVIES_FILE), moviesData);
        Files.write(Paths.get(USERS_FILE), usersData);
        rs.loadData(MOVIES_FILE, USERS_FILE);
        assertEquals(2, rs.getMovies().size());
        assertEquals(2, rs.getUsers().size());
    }
    //============================Ahmed - Statement============================//


    @Test
    void loadData_ValidFiles_ShouldLoadCorrectly() throws IOException {
        List<String> movieLines = Arrays.asList(
                "The Matrix, M001", "Action,Sci-Fi",
                "Inception, M002", "Action,Thriller"
        );
        List<String> userLines = Arrays.asList(
                "Alice, U001", "M001",
                "Bob, U002", "M002"
        );
        Files.write(new File(testMovieFile).toPath(), movieLines);
        Files.write(new File(testUserFile).toPath(), userLines);

        system.loadData(testMovieFile, testUserFile);

        assertEquals(2, system.getMovies().size());
        assertEquals(2, system.getUsers().size());
    }

    @Test
    void validateData_ValidEntries_ShouldReturnTrue() {
        system.loadData(
                writeTemp(testMovieFile, "The Matrix,TM001\nAction,Sci-Fi"),
                writeTemp(testUserFile, "Alice,12345678X\nTM001")
        );
        assertTrue(system.validateData());
    }

    @Test
    void validateData_DuplicateMovieId_ShouldReturnFalse() {
        system.loadData(
                writeTemp(testMovieFile, "The Matrix, M001\nAction\nThe Matrix Reloaded, M001\nAction"),
                writeTemp(testUserFile, "Alice, U001\nM001")
        );
        assertFalse(system.validateData());
    }

    @Test
    void generateRecommendations_ShouldRecommendCorrectly() {
        system.loadData(
                writeTemp(testMovieFile, "The Matrix, M001\nAction,Sci-Fi\nInception, M002\nAction,Thriller"),
                writeTemp(testUserFile, "Alice, U001\nM001")
        );
        system.generateRecommendations();
        User user = system.getUsers().get(0);
        assertTrue(user.getRecommendedMovies().contains("Inception"));
    }

    @Test
    void writeRecommendations_ValidData_WritesCorrectOutput() throws IOException {
        system.loadData(
                writeTemp(testMovieFile, "The Matrix, TM001\nAction,Sci-Fi\nThe Inception, TI002\nAction,Thriller"),
                writeTemp(testUserFile, "Alice, 12345678X\nTM001")
        );
        system.generateRecommendations();
        system.writeRecommendations(testOutputFile);

        List<String> output = Files.readAllLines(new File(testOutputFile).toPath());
        assertEquals("Alice,12345678X", output.get(0));
        assertTrue(output.get(1).contains("The Inception"));
    }

    @Test
    void writeRecommendations_InvalidData_WritesErrorMessage() throws IOException {
        system.loadData(
                writeTemp(testMovieFile, "The Matrix, M001\nAction\nThe Matrix Reloaded, M001\nAction"),
                writeTemp(testUserFile, "Alice, U001\nM001")
        );
        system.writeRecommendations(testOutputFile);

        List<String> output = Files.readAllLines(new File(testOutputFile).toPath());
        assertTrue(output.get(0).startsWith("ERROR"));
    }

    // Helper method to write content quickly
    private String writeTemp(String filename, String content) {
        try {
            File file = new File(filename);
            Files.write(file.toPath(), List.of(content.split("\n")));
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to write temp file: " + filename);
        }
    }
    //condition================================kareem=====================================
    @Test
    void testLoadData_MovieLineInvalidFormat() throws IOException {
        List<String> movieData = List.of("InvalidLine");
        Files.write(Paths.get(testMovieFile), movieData);
        Files.write(Paths.get(testUserFile), Collections.emptyList());

        rs.loadData(testMovieFile, testUserFile);
        assertTrue(rs.getMovies().isEmpty());
    }

    @Test
    void testLoadData_ValidMovieAndUser() throws IOException {
        List<String> movies = List.of("Movie1,M001", "Action");
        List<String> users = List.of("Alice,U12345678", "M001");
        Files.write(Paths.get(testMovieFile), movies);
        Files.write(Paths.get(testUserFile), users);

        rs.loadData(testMovieFile, testUserFile);
        assertEquals(1, rs.getMovies().size());
        assertEquals(1, rs.getUsers().size());
    }

    @Test
    void testValidateData_MovieTitleInvalid() {
        Movie badMovie = new Movie("bad title", "BT123", List.of("Action"));
        rs.setMovies(List.of(badMovie));
        rs.setUsers(Collections.emptyList());

        assertFalse(rs.validateData());
    }

    @Test
    void testValidateData_DuplicateUserId() {
        User u1 = new User("User One", "U12345678", List.of("M001"));
        User u2 = new User("User Two", "U12345678", List.of("M002"));
        rs.setMovies(Collections.emptyList());
        rs.setUsers(List.of(u1, u2));

        assertFalse(rs.validateData());
    }

    @Test
    void testGenerateRecommendations_InvalidData() {
        Movie movie = new Movie("Test Movie", "TM001", List.of("Genre"));
        User user = new User("InvalidName", "U00000000", List.of("TM001"));
        rs.setMovies(List.of(movie));
        rs.setUsers(List.of(user));

        rs.generateRecommendations();
        assertTrue(user.getRecommendedMovies().isEmpty());
    }

    @Test
    void testWriteRecommendations_ValidationFails() throws IOException {
        Movie m1 = new Movie("bad movie", "BM001", List.of("Genre"));
        rs.setMovies(List.of(m1));
        rs.setUsers(Collections.emptyList());

        rs.writeRecommendations(OUTPUT_FILE);
        List<String> lines = Files.readAllLines(Paths.get(OUTPUT_FILE));
        assertFalse(lines.isEmpty());
        assertTrue(lines.get(0).startsWith("ERROR"));
    }

    @Test
    void testWriteRecommendations_Success() throws IOException {
        Movie m1 = new Movie("Movie A", "MA123", List.of("Comedy"));
        User u1 = new User("Ali Hassan", "12345678X", List.of("MA123"));
        rs.setMovies(List.of(m1));
        rs.setUsers(List.of(u1));

        rs.generateRecommendations();
        rs.writeRecommendations(OUTPUT_FILE);

        List<String> output = Files.readAllLines(Paths.get(OUTPUT_FILE));
        assertTrue(output.contains("Ali Hassan,12345678X"));
    }
}

