import java.util.Scanner;

public class Main {

    private final static Scanner SCANNER = new Scanner(System.in);

    private final static double CONVERSION_RATE = 0.91;
    // Replace the got dam %d dol... with %s
    private final static String MAD_LIB = """
                My %s went to %s and exchanged %s
            """;

    public static void main(String[] args) {
        
        MadLib.Run(MAD_LIB);
    }

    // helper function because i'm too lazy to type System.out.
    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }
}