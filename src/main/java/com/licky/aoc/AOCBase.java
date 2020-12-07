package com.licky.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface AOCBase {
    default String getInput(int number) {
        try {
            return Files.readString(Path.of("src/main/resources/" + number + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    void partOne(String input);

    void partTwo(String input);

    private static void run(Supplier<AOCBase> supplier, int number, BiConsumer<AOCBase, String> consumer) {
        var aoc = supplier.get();
        var input = aoc.getInput(number);

        var start = System.currentTimeMillis();
        consumer.accept(aoc, input);
        var end = System.currentTimeMillis();

        System.out.println("cost time: " + (end - start));
    }

    static void run(Supplier<AOCBase> supplier, int number) {
        run(supplier, number, (aoc, input) -> {
            aoc.partOne(input);
            aoc.partTwo(input);
        });
    }

    static void runPartOne(Supplier<AOCBase> supplier, int number) {
        run(supplier, number, AOCBase::partOne);
    }

    static void runPartTwo(Supplier<AOCBase> supplier, int number) {
        run(supplier, number, AOCBase::partTwo);
    }
}
