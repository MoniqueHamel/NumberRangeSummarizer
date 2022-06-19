package numberrangesummarizer;

import java.util.*;

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer{

    /**
     *
     * @param input comma delimited string of integers.
     * @return a collection of integers sorted in ascending order.
     */
    @Override
    public Collection<Integer> collect(String input) {
        return collect(input, ",");
    }

    /**
     *
     * @param input string of integers
     * @param delimiter string specifying delimiter
     * @return a collection of integers sorted in ascending order.
     */
    public Collection<Integer> collect(String input, String delimiter) {
        if (input == null) {
            throw new NullPointerException();
        }
        if (input.trim().equals("")) {
            return new TreeSet<>();
        }

        String[] stringArray = input.split(delimiter);
        Set<Integer> setOfIntegers = new TreeSet<>();
        for (String num : stringArray) {
            String n = num.trim();

            if (n.equals("")) {
                continue;
            }

            try {
                int number = Integer.parseInt(n);
                setOfIntegers.add(number);
            } catch (NumberFormatException e) {
                throw e;
            }
        }

        return setOfIntegers;
    }

    /**
     *
     * @param input collection of integers sorted in ascending order.
     * @return a comma delimited string with contiguous ranges summarized, e.g "1, 3-7, 12".
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        return summarizeCollection(input, ", ");
    }

    /**
     *
     * @param input collection of integers sorted in ascending order.
     * @param delimiter string specifying delimiter
     * @return a delimited string with contiguous ranges summarized, e.g "1, 3-7, 12".
     */
    public String summarizeCollection(Collection<Integer> input, String delimiter) {
        if (input == null) {
            throw new NullPointerException();
        }

        if (input.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("");

        int startOfRange = input.iterator().next();
        int endOfRange = startOfRange;

        for (Integer current : input) {
            if (current > endOfRange + 1) {
                appendRange(startOfRange, endOfRange, sb);
                sb.append(delimiter);
                startOfRange = current;
            }

            endOfRange = current;
        }

        appendRange(startOfRange, endOfRange, sb);
        return sb.toString();
    }

    /**
     * Sorts and summarizes a string of integers.
     * @param input comma delimited string of integers.
     * @return a comma delimited string with contiguous ranges summarized, e.g "1, 3-7, 12".
     */
    public String collectAndSummarize(String input) {
        return summarizeCollection(collect(input));
    }

    private static void appendRange(int start, int end, StringBuilder sb) {
        if (start == end) {
            sb.append(end);
        }
        else {
            sb.append(String.format("%s-%s", start, end));
        }
    }
}
