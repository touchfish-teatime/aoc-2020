package com.licky.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

public class Day4 {

    public static void main(String[] args) throws IOException {
        partOne();

//        System.out.println(Pattern.compile("[amb|blu|brn|gry|grn|hzl|oth]").matcher("amb").matches());
//        System.out.println(Pattern.compile("\\#[0-9a-f]{6}").matcher("1123456").matches());
//        System.out.println(Pattern.compile("\\#[0-9a-f]{6}").matcher("#12w456").matches());
//        System.out.println(Pattern.compile("\\#[0-9a-f]{6}").matcher("#1234f6").matches());
    }

    private static void partOne() throws IOException {
        var input = Files.readString(Path.of("./src/main/java/com/licky/aoc/4.txt"));
        var result = Arrays.stream(input.split("\n\n"))
                .map(passport -> passport.replaceAll("\n", " "))
                .filter(passport -> {
                    var items = passport.split(" ");
                    return (
                            items.length == 8 ||
                                    items.length == 7 && Arrays.stream(items).noneMatch(item -> item.contains("cid"))
                    ) &&
                            check(items);
                }).count();

        System.out.println(result);
    }

    private static boolean check(String[] items) {

        Set<String> eyes = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        for (String item : items) {
            var i = item.split(":");
            var key = i[0];
            var value = i[1];
            switch (key) {
                case "byr":
                    var byr = Integer.parseInt(value);
                    if (byr >= 1920 && byr <= 2002) {
                        break;
                    } else {
                        return false;
                    }
                case "iyr":
                    var iyr = Integer.parseInt(value);
                    if (iyr >= 2010 && iyr <= 2020) {
                        break;
                    } else {
                        return false;
                    }
                case "eyr":
                    var eyr = Integer.parseInt(value);
                    if (eyr >= 2020 && eyr <= 2030) {
                        break;
                    } else {
                        return false;
                    }
                case "hgt":
                    if (value.endsWith("cm")) {
                        var number = Integer.parseInt(value.split("cm")[0]);
                        if (number >= 150 && number <= 193) {
                            break;
                        } else {
                            return false;
                        }
                    } else if (value.endsWith("in")) {
                        var number = Integer.parseInt(value.split("in")[0]);
                        if (number >= 59 && number <= 76) {
                            break;
                        } else {
                            return false;
                        }
                    }
                    return false;
                case "hcl":
                    if(Pattern.matches("\\#[0-9a-f]{6}", value)){
                        break;
                    }
                    return false;
                case "ecl":
                    if(eyes.contains(value)){
                        break;
                    }
                    return false;
                case "pid":
                    if(Pattern.matches("[0-9]{9}", value)){
                        break;
                    }
                    return false;
                case "cid":
                    break;
            }
        }
        return true;
    }

    private static void partTwo() {

    }
}
