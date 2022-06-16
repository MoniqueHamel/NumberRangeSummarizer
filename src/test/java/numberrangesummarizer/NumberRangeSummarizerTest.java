package numberrangesummarizer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NumberRangeSummarizerTest {

    private static final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

    @ParameterizedTest
    @MethodSource("testCollectProvider")
    public void testCollect(String input, Collection<Integer> expected) {
        Collection<Integer> actual = summarizer.collect(input);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> testCollectProvider() {
        return Stream.of(
                arguments("1, 2, 3, 4", Arrays.asList(1, 2, 3, 4)),
                arguments("-1, -2, -3, -4", Arrays.asList(-4, -3, -2, -1)),
                arguments("1,      2, 2, 4", Arrays.asList(1, 2, 2, 4)),
                arguments("80, 40, 60, -100", Arrays.asList(-100, 40, 60, 80)),
                arguments("1", Arrays.asList(1))
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
                arguments(Set.of(4, 9, 12, 3, 8), "3-4, 8-9, 12")
        );
    }
}
