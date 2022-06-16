package numberrangesummarizer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
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
}
