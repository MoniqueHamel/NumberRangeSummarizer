package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer{

    @Override
    public Collection<Integer> collect(String input) {
        String[] stringArray = input.split(",");
        List<Integer> listOfIntegers = new ArrayList<>();
        for (String num : stringArray) {
            String n = num.trim();
            int number = Integer.parseInt(n);
            listOfIntegers.add(number);
        }
        //Sort listOfIntegers from smallest to largest
        listOfIntegers.sort(Comparator.naturalOrder());

        return listOfIntegers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        StringBuilder sb = new StringBuilder("");
        List<Integer> numberList = new ArrayList<>(input);
        numberList.sort(Comparator.naturalOrder());

        int end = numberList.get(0); //previous number
        int start = end; //start of range

        for (int i = 1; i < numberList.size(); i++) {
            int current = numberList.get(i);

            if (current > end + 1) {
                appendRange(start, end, sb);
                sb.append(", ");
                start = current;
            }

            end = current;
        }

        appendRange(start, end, sb);
        return sb.toString();
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
