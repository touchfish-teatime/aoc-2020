package com.licky.aoc;

import java.util.*;

public class Day8 implements AOCBase {

    private String[][] getProgram(String input) {
        var rows = input.split("\n");

        var result = new String[rows.length][2];
        for (int i = 0; i < rows.length; i++) {
            var cols = rows[i].split(" ");
            result[i][0] = cols[0].trim();
            result[i][1] = cols[1].trim();
        }
        return result;
    }

    @Override
    public void partOne(String input) {
        var program = getProgram(input);
        Set<Integer> set = new HashSet<>(program.length);
        var i = 0;
        var result = 0;
        while (set.add(i)) {
            switch (program[i][0]) {
                case "acc":
                    result += Integer.parseInt(program[i][1]);
                    i++;
                    break;
                case "jmp":
                    i += Integer.parseInt(program[i][1]);
                    break;
                case "nop":
                    i++;
                    break;
            }
        }
        System.out.println(result);

    }

    @Override
    public void partTwo(String input) {
        var program = getProgram(input);

        HashMap<Integer, String> mapOfError = new HashMap<>();
        var list = new ArrayList<Integer>();
        for (int i = 0; i < program.length; i++) {
            if (program[i][0].equals("jmp") || program[i][0].equals("nop")) {
                mapOfError.put(i, program[i][0]);
                list.add(i);
            }
        }
        Set<Integer> set = new HashSet<>();
        var result = 0;
        while (set.size() != program.length) {
            result = 0;
            var i = 0;
            set.clear();
            while (set.add(i)) {

                if (i == program.length - 1) {
                    if (program[i][0].equals("acc")) {
                        result += Integer.parseInt(program[i][1]);
                    }
                    System.out.println(result);
                    return;
                }

                if (i < program.length) {

                    switch (program[i][0]) {
                        case "acc":
                            result += Integer.parseInt(program[i][1]);
                            i++;
                            break;
                        case "jmp":
                            if (list.get(0) == i) {
                                i++;
                                break;
                            }
                            i += Integer.parseInt(program[i][1]);
                            break;
                        case "nop":
                            if (list.get(0) == i) {
                                i += Integer.parseInt(program[i][1]);
                                break;
                            }
                            i++;
                            break;
                    }
                }
            }
            list.remove(0);
        }
    }

    public static void main(String[] args) {
        AOCBase.runPartTwo(Day8::new, 8);
    }
}
