package com.licky.aoc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day9 implements AOCBase {

    private List<Double> getInputs(String input) {
        var list = new ArrayList<Double>();
        for (String s : input.split("\n")) {
            list.add(Double.parseDouble(s));
        }
        return list;
    }

    @Override
    public void partOne(String input) {
        var list = getInputs(input);
        for (int i = 25; i < list.size(); ++i) {
            if (!isXMAS(list.get(i), list.subList(i - 25, i))) {
                System.out.println(new DecimalFormat("0").format(list.get(i)));
                break;
            }
        }

    }

    public boolean isXMAS(Double sum, List<Double> pre25) {
        for (Double integer : pre25) {
            var other = new HashSet<>(pre25);
            other.remove(integer);
            if (other.contains(sum - integer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void partTwo(String input) {
        var list = getInputs(input);

        list = list.subList(0, list.indexOf(776203571D));
        for (int i = 0; i < list.size(); i++) {
            double count = 0;
            var temp = i;
            while (count < 776203571D) {
                count += list.get(temp++);
            }
            if (count == 776203571D) {
                var max = list.subList(i, temp).stream().reduce(list.get(i), Math::max);
                var min = list.subList(i, temp).stream().reduce(list.get(i), Math::min);
                System.out.println(new DecimalFormat("0").format(max+min));
            }
        }

    }

    public static void main(String[] args) {
        AOCBase.runPartTwo(Day9::new, 9);
    }
}
