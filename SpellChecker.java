
public class SpellChecker {
    public static void main(String[] args) {
        String word = args[0];
        int threshold = Integer.parseInt(args[1]);
        String[] dictionary = readDictionary("dictionary.txt");
        String correction = spellChecker(word, threshold, dictionary);
        System.out.println(correction);
    }

    public static String tail(String str) {
        return str.substring(1);
    }

    public static int levenshtein(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        if (word2.length() == 0) {
            return word1.length();
        } else if (word1.length() == 0) {
            return word2.length();
        } else if (word1.charAt(0) == word2.charAt(0)) {
            return levenshtein(tail(word1), tail(word2));
        } else {
            int option1 = levenshtein(tail(word1), word2);
            int option2 = levenshtein(word1, tail(word2));
            int option3 = levenshtein(tail(word1), tail(word2));
            return 1 + Math.min(Math.min(option1, option2), option3);
        }
    }

    public static String[] readDictionary(String fileName) {
        String[] dictionary = new String[3000];

        In in = new In(fileName);
        for (int i = 0; i < dictionary.length; i++) {
            String word = in.readString();
            dictionary[i] = word;
        }

        return dictionary;
    }

    public static String spellChecker(String word, int threshold, String[] dictionary) {
        int minimumDistance = threshold + 1;
        String mostSimilarWord = word;
        for (int i = 0; i < dictionary.length; i++) {
            int distance = levenshtein(word, dictionary[i]);

            if (distance < minimumDistance) {
                minimumDistance = distance;
                mostSimilarWord = dictionary[i];
            }
        }

        return mostSimilarWord;
    }
}