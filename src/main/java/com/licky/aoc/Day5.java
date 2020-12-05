package com.licky.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        partOne();
    }

    private static void partOne() throws IOException {
        var input = Files.readString(Path.of("./src/main/java/com/licky/aoc/5.txt"));

        var strings = input.split("\n");
        int[] ids = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            var col = strings[i].substring(0, 7);
            var row = strings[i].substring(7, 10);

            int max = 127;
            int min = 0;
            for (char c : col.toCharArray()) {
                if (c == 'F') {
                    max = max - (max + 1 - min) / 2;
                } else {
                    min = (max + 1 - min) / 2 + min;
                }
            }

            var rowMax = 7;
            var rowMin = 0;
            for (char c : row.toCharArray()) {
                if (c == 'L') {
                    rowMax = rowMax - (rowMax + 1 - rowMin) / 2;
                } else {
                    rowMin = (rowMax + 1 - rowMin) / 2 + rowMin;
                }
            }

            ids[i] = max * 8 + rowMax;
        }

        var result = 0;
        for (int id : ids) {
            result = Math.max(result,id);
        }

        var idsSet = Arrays.stream(ids).boxed().collect(Collectors.toSet());

        for(int i =0;i<result;i++){
            if(!idsSet.contains(i) &&idsSet.contains(i-1) && idsSet.contains(i+1) ){
                System.out.println(i);
                break;
            }

        }
    }
}
