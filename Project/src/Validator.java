// Validator class to handle validation logic
class Validator {

    // Validate movie title and ID
    public String validateMovie(Movie movie) {
        // Validate title format: each word must start with a capital letter
        if (!isValidTitle(movie.getTitle())) {
            return "Movie Title " + movie.getTitle() + " is wrong";
        }

        // Validate movie ID format
        if (!isValidMovieId(movie.getMovieId())) {
            return "Movie Id " + movie.getMovieId() + " is wrong";
        }

        // Additional movie ID number uniqueness check can be done later
        return null;
    }

    // Validate user name and ID
    public String validateUser(User user) {
        // Validate user name format (alphabetic characters and spaces, no leading space)
        if (!isValidUserName(user.getUserName())) {
            return "User Name " + user.getUserName() + " is wrong";
        }

        // Validate user ID format (9 characters, starts with number, ends with optional letter)
        if (!isValidUserId(user.getUserId())) {
            return "User Id " + user.getUserId() + " is wrong";
        }

        return null;
    }

    private boolean isValidTitle(String title) {
        // Check that each word in the title starts with a capital letter
        String[] words = title.split(" ");
        for (String word : words) {
            if (!Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMovieId(String movieId) {
        // Check movie ID format: capital letters from title followed by 3 unique numbers
        // This needs to be implemented based on your rules
        // For simplicity, I'm assuming you have a rule for movie ID format
        return movieId.matches("[A-Z]+\\d{3}");  // Simplified regex for the format (e.g., "ABC123")
    }

    private boolean isValidUserName(String userName) {
        // Check user name format (alphabetic and spaces, no leading space)
        return userName.matches("[A-Za-z][A-Za-z ]*");
    }

    private boolean isValidUserId(String userId) {
        // Check user ID format: 9 characters, starts with number, ends with optional alphabetic character
        return userId.matches("\\d{8}[A-Za-z]?");
    }
}