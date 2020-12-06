package com.licky.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

public class Day6 {

    public static void main(String[] args) throws IOException {
        partTwo();
    }

    private static void partOne() throws IOException {
        var input = Files.readString(Path.of("./src/main/java/com/licky/aoc/6.txt"));

        var groups = input.split("\n\n");

        var count = 0;
        for (String group : groups) {
            var answer = group.replace("\n", "");

            var set = new HashSet<Character>();

            for (char c : answer.toCharArray()) {
                if ('a' <= c && c <= 'z') {
                    set.add(c);
                }
            }
            count += set.size();
        }
        System.out.println(count);
    }

    private static void partTwo() throws IOException {
        var input = Files.readString(Path.of("./src/main/java/com/licky/aoc/6.txt"));

        var groups = input.split("\n\n");

        var count = 0;
        for (String group : groups) {
            var person = group.split("\n").length;

            var answer = group.replace("\n", "");

            if (person == 1) {
                count += answer.length();
            } else {
                var map = new HashMap<Character, Integer>(24);

                for (char c : answer.toCharArray()) {
                    if ('a' <= c && c <= 'z') {
                        if (map.containsKey(c)) {
                            map.put(c, map.get(c) + 1);
                        } else {
                            map.put(c, 1);
                        }
                    }
                }
                for (Character character : map.keySet()) {
                    if (map.get(character) == person) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
