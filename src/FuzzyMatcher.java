import java.util.ArrayList;

// A hashmap somewhere in here could replace 90% of this i'm certain


public class FuzzyMatcher {
    private static final double RATIO_TOLERANCE = 0.04;
    private static final double MIN_SIMILARITY = 0.38;


    private static double GetMax(ArrayList<Double> arr) {
        double max = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) > max) {
                max = arr.get(i);
            }
        }
        return max;
    }

    // note to self, try using just shortest string length?
    private static double GetOrderRatio(String s1, String s2) {
        double totalLength = s1.length() + s2.length();
        double similar = 0;
        //System.out.printf("=======\n%s and %s\n=========", s1, s2);
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                similar++;
            }
        }

        return similar / totalLength;
    }

    private static double GetSameCharacterRatio(String s1, String s2) {
        double totalLength = s1.length() + s2.length();
        double similar = 0;


        for (int i = 0; i < s1.length(); i++) { // might be better to assign s1.length to a var
            for (int j = 0; j < s2.length(); j++) {
                // This has a bug where the string 'aaaaaaaa' will register as simila to 'apple' because it always finds a first
                // I don't think it'll make a huge difference in this specific use case, and I have a deadline so I'll leave it for now
                if (s1.charAt(i) == s2.charAt(j)) {
                    similar++;
                    break;
                }
            }
        }

        return similar / totalLength;
    }

    public static String GetMostSimilar(String s1, String[] toCheck) {
        System.out.printf("Searching for country most similar to %s", s1);
        // Parallel arrays
        ArrayList<String> filteredStrings = new ArrayList<>();
        ArrayList<Double> ratios = new ArrayList<>();
        int s1Length = s1.length(); // I really don't like using a # in the middle of a var name

        for (int i = 0; i < toCheck.length; i++) {
            String stringToCheck = toCheck[i];

            if (stringToCheck.equals(s1)) {
                return stringToCheck;
            }

            if (Math.abs((s1Length - stringToCheck.length())) <= 2) {
                if (s1.contains(stringToCheck)) {
                    return stringToCheck;
                }
            }

            double characterRatio = GetSameCharacterRatio(s1, stringToCheck);

            if (characterRatio >= MIN_SIMILARITY) {
                filteredStrings.add(stringToCheck);
                ratios.add(characterRatio);
            }

        }

        if (filteredStrings.isEmpty()) {
            return "no match found";
        }

        if (filteredStrings.size() == 1) {
            return filteredStrings.getFirst();
        }

//        for (int i = 0; i < filteredStrings.size(); i++) {
//            System.out.print(filteredStrings.get(i) + ", ");
//        }

        double most_similar = GetMax(ratios);

        ArrayList<String> finalists = new ArrayList<>();
        ArrayList<Double> order_ratios = new ArrayList<>();

        for (int i = 0; i < filteredStrings.size(); i++) {
            double difference = most_similar - ratios.get(i);
            if (difference < RATIO_TOLERANCE) {
                finalists.add(filteredStrings.get(i));
            }
        }


        for (int i = 0; i < finalists.size(); i++) {
            double ratio = GetOrderRatio(s1, finalists.get(i));
            order_ratios.add(ratio);
        }

        for (int i = 0; i < order_ratios.size(); i++) {
            double ratio = order_ratios.get(i) + ratios.get(i);
            ratios.set(i, ratio);
        }

        most_similar = GetMax(ratios);



        return finalists.get(ratios.indexOf(most_similar));
    }
}
