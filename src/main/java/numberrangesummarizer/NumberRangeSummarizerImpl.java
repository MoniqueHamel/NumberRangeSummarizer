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
        return null;
    }
}
