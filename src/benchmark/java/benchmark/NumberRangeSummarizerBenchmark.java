package benchmark;

import numberrangesummarizer.NumberRangeSummarizer;
import numberrangesummarizer.NumberRangeSummarizerImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

public class NumberRangeSummarizerBenchmark {
    private static final int NANOSECONDS_IN_SECONDS = 1000000000;

    private static final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
    private static final Random rng = new Random();

    public static void main(String[] args) {
        runCollectBenchmark(10000, 10000);
        runSummarizeCollectionBenchmark(10000, 10000, 10000);
    }

    private static String generateString(int stringLength) {
        ArrayList<String> arr = new ArrayList<>(stringLength);

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

    private static void runCollectBenchmark(int numStrings, int stringLength) {
        String[] inputStrings = generateInputStrings(numStrings, stringLength);

        System.out.println("Starting collect() benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < numStrings; i++) {
            summarizer.collect(inputStrings[i]);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        long averageTime = elapsedTime / numStrings;

        System.out.printf(Locale.US, "Processed %s strings of length %s in %.3f seconds.%n", numStrings, stringLength, ((double)elapsedTime / NANOSECONDS_IN_SECONDS));
        System.out.println("Average execution time (nanoseconds): " + averageTime);
        System.out.printf(Locale.US, "Average execution time (seconds): %.9f%n", ((double) averageTime / NANOSECONDS_IN_SECONDS));
    }

    private static Collection<Integer> generateCollection(int collectionSize, int maxNumber) {
        Collection<Integer> collection = new ArrayList<>(collectionSize);

        for (int i = 0; i < collectionSize; i++) {
            collection.add(rng.nextInt(maxNumber));
        }

        return collection;
    }

    private static void runSummarizeCollectionBenchmark(int numCollections, int collectionSize, int maxNumber) {
        System.out.println("Starting summarizeCollection() benchmark...");

        long elapsedTime = 0;

        for (int i = 0; i < numCollections; i++) {
            // Generate collections one at a time to prevent running out of heap space
            Collection<Integer> collection = generateCollection(collectionSize, maxNumber);
            long startTime = System.nanoTime();
            summarizer.summarizeCollection(collection);
            elapsedTime += System.nanoTime() - startTime;
        }
        long averageTime = elapsedTime / numCollections;

        System.out.printf(Locale.US, "Processed %s collections of size %s in %.3f seconds.%n", numCollections, collectionSize, ((double)elapsedTime / NANOSECONDS_IN_SECONDS));
        System.out.println("Average execution time (nanoseconds): " + averageTime);
        System.out.printf(Locale.US, "Average execution time (seconds): %.9f%n", ((double) averageTime / NANOSECONDS_IN_SECONDS));
    }
}
