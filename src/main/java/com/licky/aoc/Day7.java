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
        var all = getBags(input);

        var subBags = Set.of(new Bag("shiny gold", 1));
        var result = new HashSet<Bag>();
        do {
            Set<Bag> finalSubBags = subBags;
            subBags = all.stream().filter(bag -> bag.getSubBags().stream().anyMatch(finalSubBags::contains)).collect(Collectors.toSet());
            result.addAll(subBags);
        } while (subBags.size() != 0);

//        var result = all.stream().filter(bag -> !bag.equals(gold) && containsGold(bag, all)).collect(Collectors.toList());

        System.out.println(result.size());
    }


    private static final Bag gold = new Bag("shiny gold", 1);

    private boolean containsGold(Bag bag, Set<Bag> all) {
        if (bag.getSubBags() == null || bag.getSubBags().size() == 0) {
            return false;
        }

        return bag.getSubBags().stream()
                .anyMatch(sub -> sub.equals(gold) ||
                        containsGold(
                                all.stream().filter(a -> a.equals(sub))
                                        .findAny()
                                        .orElseGet(() -> new Bag("", 1)),
                                all
                        )
                );

    }

    private int getSubBagsCount(Bag bag, Set<Bag> all, boolean root) {
        if (root && bag.getSubBags().size() == 0) {
            return 0;
        } else if (bag.getSubBags().size() == 0) {
            return bag.getCount();
        }

        var count = bag.getSubBags().stream().map(sub ->
                sub.getCount() * getSubBagsCount(
                        all.stream().filter(a -> a.equals(sub))
                                .findAny().get(),
                        all,
                        false
                )
        ).reduce(0, Integer::sum) + bag.getCount();

        return count;

    }

    private int some(Bag bag, Set<Bag> all) {
        if (bag.getSubBags() == null || bag.getSubBags().size() == 0) {
            return bag.getCount();
        }

        var count = bag.getSubBags().stream()
                .map(sub ->
                        sub.getCount() * some(
                                all.stream().filter(a -> a.equals(sub))
                                        .findAny()
                                        .orElseGet(() -> new Bag("", 1)),
                                all
                        )
                ).reduce(0, Integer::sum) + bag.getCount();
        return count;
    }

    public Set<Bag> getBags(String input) {
        var bagLines = input.split("\n");
        Set<Bag> all = new HashSet<>(bagLines.length);
        for (String bagLine : bagLines) {

            var bags = bagLine.split("bags contain");
            var bag = new Bag(bags[0].trim(), 1);
            if (!bags[1].contains("no other bags")) {
                var subBags = bags[1].replaceAll("[,.]", "").split("bags|bag");
                for (String subBag : subBags) {
                    var b = subBag.trim().split(" ");
                    bag.getSubBags().add(new Bag(b[1] + " " + b[2], Integer.parseInt(b[0])));
                }
            }
            all.add(bag);
        }
        return all;
    }

    public void partTwo(String input) {
        var all = getBags(input);
        var count = all.stream().filter(bag -> bag.equals(gold))
                .findAny()
                .map(bag ->
                        getSubBagsCount(bag, all, true)
                )
                .get()-1;
        System.out.println(count);
    }

    public static void main(String[] args) {
        AOCBase.runPartTwo(Day7::new, 7);
    }


    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Bag {

        public Bag(String color, int count) {
            this.color = color;
            this.count = count;
        }

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        int count;

        String color;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        Set<Bag> subBags = new HashSet<>();
    }
}
