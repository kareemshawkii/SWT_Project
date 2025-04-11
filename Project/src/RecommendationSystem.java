import java.io.*;
import java.util.*;
import java.util.regex.*;

public class RecommendationSystem {

    private static List<Movie> movies;
    private static List<User> users;
    private static FileHandler fileHandler = new FileHandler();
    private static Validator validator = new Validator();


    private static Set<String> generateRecommendations(User user) {
        Set<String> recommendedMovies = new HashSet<>();
        Set<String> likedGenres = new HashSet<>();

        // Get genres from liked movies
        for (String movieId : user.getLikedMovieIds()) {
            for (Movie movie : movies) {
                if (movie.getMovieId().equals(movieId)) {
                    likedGenres.addAll(Arrays.asList(movie.getGenres()));
                }
            }
        }

        // Recommend movies from the same genres
        for (Movie movie : movies) {
            if (!user.getLikedMovieIds().contains(movie.getMovieId())) {
                for (String genre : likedGenres) {
                    if (Arrays.asList(movie.getGenres()).contains(genre)) {
                        recommendedMovies.add(movie.getTitle());
                        break;
                    }
                }
            }
        }

        return recommendedMovies;
    }
    
    public static void main(String[] args) {
        try {
            // Load and validate movies and users from files
            movies = fileHandler.readMovies("movies.txt");
            users = fileHandler.readUsers("users.txt");

            // Validate movies
            for (Movie movie : movies) {
                String movieValidationError = validator.validateMovie(movie);
                if (movieValidationError != null) {
                    fileHandler.writeToFile("Recommendations.txt", "ERROR: " + movieValidationError);
                    return;  // Stop further processing if there is an error
                }
            }

            // Validate users
            for (User user : users) {
                String userValidationError = validator.validateUser(user);
                if (userValidationError != null) {
                    fileHandler.writeToFile("Recommendations.txt", "ERROR: " + userValidationError);
                    return;  // Stop further processing if there is an error
                }
            }

            // Generate recommendations for each user
            for (User user : users) {
                Set<String> recommendedMovies = generateRecommendations(user);
                String output = user.getUserName() + ", " + user.getUserId() + "\n" + String.join(", ", recommendedMovies);
                fileHandler.writeToFile("Recommendations.txt", output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
