import java.io.File;
import java.util.*;


public class RecommendationSystem {

    private List<Movie> movies = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    private List<String> errors = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler(); // Assuming FileHandler class exists

    public RecommendationSystem() {
    }

    // --- Getters and Setters ---
    public List<Movie> getMovies() {
        return this.movies;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    // --- Load Data Method ---
    public void loadData(String moviesFile, String usersFile) {
        List<String> movieLines = fileHandler.readFile(moviesFile);
        List<String> userLines = fileHandler.readFile(usersFile);
        this.movies.clear(); // Clear existing data before loading
        this.users.clear();
        this.errors.clear();

        // Process Movies
        for (int i = 0; i < movieLines.size(); ++i) {
            String line = movieLines.get(i);
            if (line == null || line.trim().isEmpty()) continue; // Skip empty lines

            String[] movieParts = line.split(",");
            if (movieParts.length != 2) {
                this.errors.add("ERROR: Invalid movie line format at line " + (i + 1) + ": " + line);
                i++; // Attempt to skip potential genre line
                continue;
            }
            String title = movieParts[0].trim();
            String movieId = movieParts[1].trim();
            // *** ADDED CHECK: Validate movieId right after extraction ***
            if (movieId.isEmpty()) {
                this.errors.add("ERROR: Missing Movie ID for title '" + title + "' at line " + (i + 1));
                // Attempt to skip the next line as it's likely the genre for the invalid movie
                if (i + 1 < movieLines.size()) {
                    i++;
                }
                continue; // Skip processing this movie further
            }
            // *** END OF ADDED CHECK ***

            if (i + 1 < movieLines.size()) {
                String genreLine = movieLines.get(i + 1);
                if (genreLine == null || genreLine.trim().isEmpty()) {
                    this.errors.add("ERROR: Missing or empty genre line for movie: " + title + " at line " + (i + 2));
                    i++; // Move past the empty line
                    continue;
                }
                List<String> genres = new ArrayList<>();
                for (String genre : genreLine.split(",")) {
                    if (!genre.trim().isEmpty()) { // Avoid adding empty genres
                        genres.add(genre.trim());
                    }
                }
                if (genres.isEmpty()){
                    this.errors.add("ERROR: No valid genres found for movie: " + title + " at line " + (i + 2));
                }
                this.movies.add(new Movie(title, movieId, genres));
                i++; // Increment i because we processed the genre line
            } else {
                this.errors.add("ERROR: Missing genre line for movie: " + title);
            }
        }

        // Process Users
        for (int i = 0; i < userLines.size(); ++i) {
            String line = userLines.get(i);
            if (line == null || line.trim().isEmpty()) continue; // Skip empty lines

            String[] userParts = line.split(",");
            if (userParts.length != 2) {
                this.errors.add("ERROR: Invalid user line format at line " + (i + 1) + ": " + line);
                i++; // Attempt to skip potential liked movies line
                continue;
            }

            String name = userParts[0].trim();
            String userId = userParts[1].trim();

            if (i + 1 < userLines.size()) {
                String likedLine = userLines.get(i + 1);
                if (likedLine == null) { // Check for null explicitly
                    this.errors.add("ERROR: Missing liked movies line for user: " + name);
                    i++; // Move past this line index
                    continue;
                }
                List<String> likedMovies = new ArrayList<>();
                // Handle empty liked list correctly
                if (!likedLine.trim().isEmpty()) {
                    for (String likedId : likedLine.split(",")) {
                        if (!likedId.trim().isEmpty()){ // Avoid adding empty IDs
                            likedMovies.add(likedId.trim());
                        }
                    }
                }
                this.users.add(new User(name, userId, likedMovies));
                i++; // Increment i because we processed the liked movies line
            } else {
                this.errors.add("ERROR: Missing liked movies line for user: " + name);
            }
        }
        if (!this.errors.isEmpty()) {
            System.err.println("Errors occurred during data loading:");
            this.errors.forEach(System.err::println);
        }
    }


    // --- Validate Data Method ---

    public boolean validateData() {
        Set<String> movieIds = new HashSet<>();
        Set<String> userIds = new HashSet<>();
        for (Movie movie : movies) {

            if (!Validator.validateTitle(movie.getTitle())) {
                errors.add("ERROR: Invalid movie title: " + movie.getTitle());
                return false;
            }

            if (!Validator.validateMovieId(movie.getMovieId(), movie.getTitle(), movies)) {
                errors.add("ERROR: Invalid movie ID: " + movie.getMovieId());
                return false;
            }

            if (!movieIds.add(movie.getMovieId())) {
                errors.add("ERROR: Duplicate movie ID: " + movie.getMovieId());
                return false;
            }
        }

        for (User user : users) {
            if (!Validator.validateUserName(user.getName())) {
                errors.add("ERROR: Invalid user name: " + user.getName());
                return false;
            }

            if (!Validator.validateUserId(user.getUserId(), userIds)) {
                errors.add("ERROR: Invalid user ID: " + user.getUserId());
                return false;
            }

            if (!userIds.add(user.getUserId())) {
                errors.add("ERROR: Duplicate user ID: " + user.getUserId());
                return false;
            }


            for (String likedMovieId : user.getLikedMovieIds()) {
                if (!movieIds.contains(likedMovieId)) {
                    errors.add("ERROR: User " + user.getUserId() + " has invalid liked movie ID: " + likedMovieId);
                    return false;
                }
            }
        }

        return true;
    }



    public void generateRecommendations() {
        for (User user : users) {
            Set<String> likedGenres = new HashSet<>();
            if(!validateData()){

                user.setRecommendedMovies(new ArrayList<>(' '));

            }
            // Get genres of liked movies
            for (String likedMovieId : user.getLikedMovieIds()) {
                for (Movie movie : movies) {
                    if (movie.getMovieId().equals(likedMovieId.trim())) {
                        likedGenres.addAll(movie.getGenres());
                    }
                }
            }

            // Generate recommended movies based on liked genres
            Set<String> recommendedTitles = new LinkedHashSet<>();
            for (String genre : likedGenres) {
                for (Movie movie : movies) {
                    if (movie.getGenres().contains(genre) && !user.getLikedMovieIds().contains(movie.getMovieId())) {
                        recommendedTitles.add(movie.getTitle());
                    }
                }
            }

            // Set recommended movies for the user
            user.setRecommendedMovies(new ArrayList<>(recommendedTitles));
        }
    }


    // Write the generated recommendations to an output file
    public void writeRecommendations(String outputFile) {
        List<String> content = new ArrayList<>();

        if(!validateData()){
            content.add(errors.get(0));
            // Write the first error if there are any
        }

        else {
            for (User user : users) {
                content.add(user.getName() + "," + user.getUserId());
                content.add(String.join(",", user.getRecommendedMovies()));
            }
        }

        fileHandler.writeFile(outputFile, content);
    }



    ////////////////////////////////////////////// Main //////////////////////////////////////////////////////

    // Main method to run the application
    public static void main(String[] args) {


        String moviesFile = "src/main/resources/movies.txt";
        String usersFile = "src/main/resources/users.txt";

        // Check if the files exist before proceeding
        File movieFile = new File(moviesFile);
        File userFile = new File(usersFile);

        if (!movieFile.exists()) {
            System.out.println("ERROR: The movie file doesn't exist at " + moviesFile);
        }

        if (!userFile.exists()) {
            System.out.println("ERROR: The user file doesn't exist at " + usersFile);
        }

        // Proceed if files exist
        if (movieFile.exists() && userFile.exists()) {
            RecommendationSystem recommendationSystem = new RecommendationSystem();


            String outputFile = "src/main/resources/recommendations.txt";

            // Load data from files
            recommendationSystem.loadData(moviesFile, usersFile);


            // Generate recommendations
            recommendationSystem.generateRecommendations();

            // Write recommendations to the output file
            recommendationSystem.writeRecommendations(outputFile);

        }
    }
}