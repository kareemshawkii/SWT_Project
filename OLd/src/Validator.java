import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validateTitle(String title) {
        if(title.charAt(0)==' '||title.charAt(title.length()-1)==' ') return false ;
        String[] words = title.trim().split(" ");
        for (String word : words) {
            if (word.isEmpty() || !Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUnique(List<Movie> movies) {
        ArrayList<Integer> dynamicArray = new ArrayList<>();

        // Validate movies
        for (Movie movie : movies) {
            Pattern pattern = Pattern.compile("-?\\d+"); // supports negative numbers too
            Matcher matcher = pattern.matcher(movie.getMovieId());

            while (matcher.find()) {
                dynamicArray.add(Integer.parseInt(matcher.group()));
            }

            for (int i = 0; i < dynamicArray.size() - 1; i++) {
                if (dynamicArray.get(i) == dynamicArray.get(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateMovieId(String movieId, String title, List<Movie> movies) {
        StringBuilder capitalLetters = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (Character.isUpperCase(c)) {
                capitalLetters.append(c);
            }
        }

        String expectedStart = capitalLetters.toString();
        if (!movieId.startsWith(expectedStart)) {
            return false;
        }

        String digits = movieId.substring(expectedStart.length());
        if (!digits.matches("\\d{3}")) {
            return false;
        }

        Set<Character> digitSet = new java.util.HashSet<>();
        for (char c : digits.toCharArray()) {
            digitSet.add(c);
        }

        if(!isUnique(movies)) { // fix movie id unique bug
            return false;
        }

        return true;
    }

    public static boolean validateUserName(String name) {
        if (name.startsWith(" ")) return false;
        return name.matches("^[A-Za-z]+(?: [A-Za-z]+)*$"); //  "^[A-Za-z ]+$";  [A-Za-z ]+  // Solved username bug
    }

    public static boolean validateUserId(String userId, Set<String> existingIds) {
        if (userId.length() != 9) return false;
        if (!userId.matches("^\\d{8}[A-Za-z]?$")) return false; // Solved user id bug
        return !existingIds.contains(userId);
    }

}
