package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Please, enter the secret code's length:");
        System.out.print(">");
        String nn = sc.nextLine();
        if (nn.charAt(0)=='0'){
            System.out.printf("Error: %s isn't a valid number.", nn.charAt(0));
            return;
        }
        char[] chars = nn.toCharArray();
        for (Character current : chars
        ) {
            if (Character.isLetter(current) || !Character.isDigit(current) || Character.isAlphabetic(current) || Character.isWhitespace(current)) {
                System.out.printf("Error: %s isn't a valid number.", nn);
                return;
            }

        }
        int n = Integer.parseInt(nn);
        System.out.println("Input the number of possible symbols in the code:");
        System.out.print(">");


        int s = sc.nextInt();
        if (s > 36) {
            System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }
        if (s < n) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", n, s);
            return;
        }

        if (n > 36) {

            System.out.printf("Error: can't generate a secret number with a length of %s because there aren't enough unique digits.", n);
        } else {

            StringBuilder zvezdo4ki = new StringBuilder();
            for (int i = 0; i < n; i++) {
                zvezdo4ki.append("*");
            }
            StringBuilder possibleRangeOfLetters = new StringBuilder();
            String ABC = "abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < Integer.parseInt(String.valueOf(s)) - 10; i++) {
                possibleRangeOfLetters.append(ABC.charAt(i));
            }
            String range = "";
            if (!(possibleRangeOfLetters.length() == 0)) {
                range = possibleRangeOfLetters.charAt(0) + "-" + possibleRangeOfLetters.charAt(possibleRangeOfLetters.length() - 1);
            }


            String secret = getUnicalSecret(n, possibleRangeOfLetters);
            System.out.printf("The secret is prepared: %s (0-9, %s).\n", zvezdo4ki, range);
            System.out.println(secret);
            System.out.println("Okay, let's start a game!");

            int turnCounter = 1;
            boolean isWin = false;

            while (!isWin) {
                Scanner sc2 = new Scanner(System.in);
                System.out.printf("Turn %d:\n", turnCounter);
                System.out.print(">");
                String input = sc2.nextLine();
                String result = method(input, secret);
                if (result.contains(String.format("%s bulls", n))) {
                    System.out.println(result);
                    isWin = true;

                } else {
                    turnCounter++;
                    System.out.println(result);

                }

            }
            System.out.println("Congratulations! You guessed the secret code.");

        }


    }

    public static String getUnicalSecret(int n, StringBuilder range) {

        String unicalSecret = "0";
        while (unicalSecret.length() != n) {
            double pseudoRandomNumber = Math.random();
            StringBuilder sb = new StringBuilder(String.valueOf(pseudoRandomNumber).replaceAll("[0.]", "1"));
            String pseudoRandomNumberReversed = sb.reverse().toString();
            unicalSecret = generateNumberAndLetters(n, pseudoRandomNumberReversed, range);
            unicalSecret = checkNumberForUnical(unicalSecret);

        }
        return unicalSecret;
    }

    private static String checkNumberForUnical(String secret) {
        StringBuilder unical = new StringBuilder();
        for (int i = 0; i < secret.length(); i++) {
            if (!(unical.toString().contains(String.valueOf(secret.charAt(i))))) {
                unical.append((secret.charAt(i)));
            }
        }
        return unical.toString();
    }

    private static String generateNumberAndLetters(int n, String pseudoRandomNumber, StringBuilder range) {
        int boundInterval = range.length() + 10 + 1;
        Random random = new Random();

        String numbersAsString = pseudoRandomNumber;

        StringBuilder generated = new StringBuilder(numbersAsString.substring(0, 10));

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < range.length(); j++) {
                generated.insert(random.nextInt(10), range.charAt(j));
            }
        }

        StringBuilder generated2 = new StringBuilder();
        for (int i = 0; i < n; i++) {
            generated2.append(generated.charAt(i));
        }

        return generated2.toString();
    }

    public static String method(String input, String secret) {

        int cows = 0;
        int bulls = 0;
        String result = "";

        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < secret.length(); j++) {
                if (input.charAt(i) == secret.charAt(i)
                        && i == j) {
                    bulls++;
                }
                if (input.charAt(i) == secret.charAt(j) &&
                        input.charAt(j) != secret.charAt(j)) {
                    cows++;
                }
            }

        }
        if (cows == 0 && bulls == 0) {
            result = String.format("Grade: None.");
        }
        if (cows == 0 && bulls > 0) {
            if (bulls == 1) {
                result = String.format("Grade: %d bull.", bulls);
            }
            result = String.format("Grade: %d bulls.", bulls);
        } else if (bulls == 0 && cows > 0) {
            if (cows > 1) {
                result = String.format("Grade: %d cows.", cows);
            }
            result = String.format("Grade: %d cow.", cows);
        } else if (bulls > 0 && cows > 0) {
            if (bulls > 1 && cows > 1) {
                result = String.format("Grade: %d bulls and %d cows.", bulls, cows);
            }

            if (bulls == 1 && cows == 1) {
                result = String.format("Grade: %d bull and %d cow.", bulls, cows);
            }

            if (bulls == 1 && cows > 1) {
                result = String.format("Grade: %d bull and %d cows.", bulls, cows);
            }

            if (bulls > 1 && cows == 1) {
                result = String.format("Grade: %d bulls and %d cow.", bulls, cows);
            }


        }
        return result;
    }


}
