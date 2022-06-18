package benchmark;

import numberrangesummarizer.NumberRangeSummarizer;
import numberrangesummarizer.NumberRangeSummarizerImpl;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class NumberRangeSummarizerBenchmark {
    public static void main(String[] args) {
        NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
        int numStrings = 10000;
        int stringLength = 10000;
        String[] inputStrings = generateInputStrings(numStrings, stringLength);

        System.out.println("Starting benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < numStrings; i++) {
            summarizer.collect(inputStrings[i]);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        long averageTime = elapsedTime / numStrings;

        System.out.printf(Locale.US, "Processed %s strings of length %s in %.3f seconds.%n", numStrings, stringLength, ((double)elapsedTime / 1000000000));
        System.out.println("Average execution time (nanoseconds): " + averageTime);
        System.out.printf(Locale.US, "Average execution time (seconds): %.9f%n", ((double) averageTime / 1000000000));
    }

    private static String generateString(int stringLength) {
        ArrayList<String> arr = new ArrayList<>(stringLength);
        Random rng = new Random();

        for (int i = 0; i < stringLength; i++) {
            arr.add(String.valueOf(rng.nextInt()));
        }

        return String.join(",", arr);
    }

    private static String[] generateInputStrings(int arraySize, int stringLength) {
        String[] inputArray = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            inputArray[i] = generateString(stringLength);
        }

        return inputArray;
    }
}
