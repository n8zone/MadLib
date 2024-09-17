import java.util.Scanner;

public class MadLib {
    // TODO: Move program logic from Main into here
    private final static Scanner SCANNER = new Scanner(System.in);
    public static void Run(final String MAD_LIB) {

        // INPUT VARIABLES //
        String noun, country;
        String currencyString = "no money because he is incredibly poor and shouldn't be travelling anyway.";

        double dollars, conversionRate = 0;


        // INPUT PROMPTING //
        System.out.println("Enter a noun");
        noun = SCANNER.nextLine();

        System.out.println("Enter a country");
        country = SCANNER.nextLine().toLowerCase();

        do {
            System.out.println("Enter any number greater than or equal to 0");
            dollars = SCANNER.nextDouble();
        } while (dollars < 0);


        // DATA PROCESSING //

        if (dollars > 0) {
            String currencyCode = "";
            try {
                currencyCode = CurrencyConverter.GetCurrencyCode(country);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                currencyCode = CurrencyConverter.GetCurrencyCode(FuzzyMatcher.GetMostSimilar(country, CurrencyConverter.allCountries));
            }


            conversionRate = CurrencyConverter.GetRate(currencyCode);

            // It threw me off that %f was used for a double, but I learned that floats are promoted to doubles when passed into String.format
            // Update: Sep 12, I got this fact mixed up on the quiz and lost points :^(
            String currencyFormat = "%.2f USD for %.2f %s";

            double convertedMoney = dollars * conversionRate;
            currencyString = String.format(currencyFormat, dollars, convertedMoney, currencyCode);
        }



        String finalMadLib = String.format(MAD_LIB, noun, country, currencyString).trim();

        System.out.println(finalMadLib);
    }
}
