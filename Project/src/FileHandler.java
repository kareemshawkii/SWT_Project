import java.io.*;
import java.util.*;

public class FileHandler {

    // Read movie data from the file and return a list of Movie objects
    public List<Movie> readMovies(String fileName) throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] movieDetails = line.split(", ");
                if (movieDetails.length == 2) {
                    String title = movieDetails[0];
                    String movieId = movieDetails[1];
                    String genreLine = reader.readLine();  // Read the next line for genres
                    String[] genres = genreLine.split(", ");  // Split the genres into an array
                    movies.add(new Movie(movieId, title, genres));
                }
            }
        }
        return movies;
    }

    // Read user data from the file and return a list of User objects
    public List<User> readUsers(String fileName) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(", ");
                if (userDetails.length == 2) {
                    String userName = userDetails[0];
                    String userId = userDetails[1];
                    String[] likedMovies = reader.readLine().split(", ");  // Read liked movie IDs
                    Set<String> likedMovieIds = new HashSet<>(Arrays.asList(likedMovies));
                    users.add(new User(userId, userName, likedMovieIds));
                }
            }
        }
        return users;
    }

    // Write the output to a file
    public void writeToFile(String fileName, String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(output);
            writer.newLine(); // Add a new line after each output
        }
    }
}
