import java.io.*;
import java.util.*;

public class RecommendationSystem {
    public List<Movie> getMovies() {
        return movies;
    }

    public List<User> getUsers() {
        return users;
    }

    private List<Movie> movies = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();       // put in the uml


    // Load movie and user data from the specified files
    public void loadData(String moviesFile, String usersFile) {
        List<String> movieLines = fileHandler.readFile(moviesFile);
        List<String> userLines = fileHandler.readFile(usersFile);

        // Parse movies
        for (int i = 0; i < movieLines.size(); i++) {
            String line = movieLines.get(i);
            String[] movieParts = line.split(",");

            // Check if the line has exactly 2 parts (title and ID)
            if (movieParts.length != 2) {
                errors.add("ERROR: Invalid movie line format at line " + (i + 1));
                continue;
            }

            String title = movieParts[0].trim();
            String movieId = movieParts[1].trim();

            // Make sure we're not going beyond the bounds of the list
            if (i + 1 < movieLines.size()) {
                String genreLine = movieLines.get(i + 1);  // Genres are on the next line
                List<String> genres = Arrays.asList(genreLine.split(","));
                movies.add(new Movie(title, movieId, genres));
                i++;  // Skip the next line since it's the genre line
            }
        }

        // Parse users
        for (int i = 0; i < userLines.size(); i++) {
            String line = userLines.get(i);
            String[] userParts = line.split(",");

            // Check if the line has exactly 2 parts (name and userId)
            if (userParts.length != 2) {
                errors.add("ERROR: Invalid user line format");
                return;
            }

            String name = userParts[0].trim();
            String userId = userParts[1].trim();

            // Make sure we're not going beyond the bounds of the list
            if (i + 1 < userLines.size()) {
                String likedLine = userLines.get(i + 1);  // Liked movies are on the next line
                List<String> likedMovies = Arrays.asList(likedLine.split(","));

                // Add the user and their liked movies
                users.add(new User(name, userId, likedMovies));

                // Skip the next line because it's the liked movies line
                i++;  // Increment i to skip the liked movie line
            }
        }


    }

    // Validate movie and user data
    public boolean validateData() {
        Set<String> movieIds = new HashSet<>();
        Set<String> userIds = new HashSet<>();

        for (Movie movie : movies) {
            if (!Validator.validateTitle(movie.getTitle())) {
                errors.add("ERROR: Invalid movie title: " + movie.getTitle());
                return false;
            }

            if (!Validator.validateMovieId(movie.getMovieId(), movie.getTitle(), movieIds)) {
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
        }

        return true;
    }

    // Generate movie recommendations for users based on liked movies and genres
    public void generateRecommendations() {
        for (User user : users) {
            Set<String> likedGenres = new HashSet<>();
            if(!validateData()){
                user.setRecommendedMovies(null);
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


        String moviesFile = "D://Documents//GitHub//SWT_Project//out//production//Project//movies.txt";
        String usersFile = "D:/Documents/GitHub/SWT_Project/out/production/Project/users.txt";

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


        String outputFile = "recommendations.txt";

        // Load data from files
        recommendationSystem.loadData(moviesFile, usersFile);


        // Generate recommendations
        recommendationSystem.generateRecommendations();

        // Write recommendations to the output file
        recommendationSystem.writeRecommendations(outputFile);

    }
}
}
