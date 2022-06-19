package numberrangesummarizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NumberRangeSummarizerImplTest {

    private static final NumberRangeSummarizerImpl summarizer = new NumberRangeSummarizerImpl();

    @ParameterizedTest
    @MethodSource("testCollectProvider")
    public void testCollect(String input, Collection<Integer> expected) {
        Collection<Integer> actual = summarizer.collect(input);
        Set<Integer> expectedSet = new TreeSet<>(expected);
        assertEquals(expectedSet, actual);
    }

    static Stream<Arguments> testCollectProvider() {
        return Stream.of(
                arguments("1, 2, 3, 4", Arrays.asList(1, 2, 3, 4)),
                arguments("-1, -2, -3, -4", Arrays.asList(-4, -3, -2, -1)),
                arguments("1,      2, 2, 4", Arrays.asList(1, 2, 2, 4)),
                arguments("80, 40, 60, -100", Arrays.asList(-100, 40, 60, 80)),
                arguments("", Arrays.asList()),
                arguments("1", Arrays.asList(1)),
                arguments("1,,,2, 3", Arrays.asList(1, 2, 3))
        );
    }

    @Test
    public void testCollect_invalidDelimiter() {
        assertThrows(NumberFormatException.class, () ->
                summarizer.collect("1 2 3"));
    }

    @ParameterizedTest
    @MethodSource("invalidCollectionProvider")
    public <T extends Throwable> void testCollect_invalidString(String input, Class<T> expectedError) {
        assertThrows(expectedError, () ->
                summarizer.collect(input));
    }

    static Stream<Arguments> invalidCollectionProvider() {
        return Stream.of(
                arguments("1.255, 3.755", NumberFormatException.class),
                arguments(null, NullPointerException.class),
                arguments("three", NumberFormatException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("summarizeCollectionProvider")
    public void testSummarizeCollection(Collection<Integer> input, String expected) {
        String actual = summarizer.summarizeCollection(input);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> summarizeCollectionProvider() {
        return Stream.of(
                arguments(Arrays.asList(1, 2, 3, 4), "1-4"),
                arguments(Arrays.asList(1, 2, 3, 5, 7, 9, 10, 11), "1-3, 5, 7, 9-11"),
                arguments(Arrays.asList(-2, -1, 0, 1, 5), "-2-1, 5"),
                arguments(Arrays.asList(2, 2, 2, 2), "2"),
                arguments(Arrays.asList(2, 2, 2, 2, 3, 4, 8), "2-4, 8"),
                arguments(Arrays.asList(0, 2, 2, 2, 3, 4, 8), "0, 2-4, 8"),
                arguments(Arrays.asList(), "")
        );
    }

    @Test
    public void testSummarizeCollection_stack() {
        Stack<Integer> testStack = new Stack<>();

        testStack.push(-5);
        testStack.push(0);
        testStack.push(2);
        testStack.push(3);
        testStack.push(4);

        String actual = summarizer.summarizeCollection(testStack);
        String expected = "-5, 0, 2-4";

        assertEquals(expected, actual);
    }

    @Test
    public void testSummarizeCollection_nullInput() {
        assertThrows(NullPointerException.class, () ->
                summarizer.summarizeCollection(null));
    }

    @ParameterizedTest
    @MethodSource("collectAndSummarizeProvider")
    public void collectAndSummarize(String input, String expected) {
        String actual = summarizer.collectAndSummarize(input);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> collectAndSummarizeProvider() {
        return Stream.of(
                arguments("1, 2, 3, 4", "1-4"),
                arguments("-1, -2, -3, -4", "-4--1"),
                arguments("80, 40, 60, -100", "-100, 40, 60, 80"),
                arguments("", ""),
                arguments("1", "1")
        );
    }

}
