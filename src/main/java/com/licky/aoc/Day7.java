package com.licky.aoc;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 implements AOCBase {

    public void partOne(String input) {
        var bagLines = input.split("\n");
        Set<Bag> all = new HashSet<>(bagLines.length);
        for (String bagLine : bagLines) {

            var bags = bagLine.split("bags contain");
            var bag = new Bag(bags[0].trim());
            if (!bags[1].contains("no other bags")) {
                var subBags = bags[1].replaceAll("[,.0-9]", "").split("bags|bag");
                for (String subBag : subBags) {
                    bag.getSubBags().add(new Bag(subBag.trim()));
                }
            }
            all.add(bag);
        }

        var subBags = Set.of(new Bag("shiny gold"));
        var result = new HashSet<Bag>();
        do {
            Set<Bag> finalSubBags = subBags;
            subBags = all.stream().filter(bag -> bag.getSubBags().stream().anyMatch(finalSubBags::contains)).collect(Collectors.toSet());
            result.addAll(subBags);
        } while (subBags.size() != 0);

//        var result = all.stream().filter(bag -> !bag.equals(gold) && containsGold(bag, all)).collect(Collectors.toList());

        System.out.println(result.size());
    }


    private static final Bag gold = new Bag("shiny gold");

    private boolean containsGold(Bag bag, Set<Bag> all) {
        if (bag.getSubBags() == null || bag.getSubBags().size() == 0) {
            return false;
        }

        return bag.getSubBags().stream()
                .anyMatch(sub -> sub.equals(gold) ||
                        containsGold(
                                all.stream().filter(a -> a.equals(sub))
                                        .findAny()
                                        .orElseGet(() -> new Bag("")),
                                all
                        )
                );

    }

    public void partTwo(String input) {
    }

    public static void main(String[] args) {
        AOCBase.runPartOne(Day7::new, 7);
    }


    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Bag {

        public Bag(String color) {
            this.color = color;
        }

        String color;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        Set<Bag> subBags = new HashSet<>();
    }
}
