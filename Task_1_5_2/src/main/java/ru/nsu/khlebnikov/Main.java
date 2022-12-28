package ru.nsu.khlebnikov;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Main method that we will run.
 */
public class Main {
    /**
     * Main.
     *
     * @param args arguments
     * @throws ParseException exception thrown if problems with parsing time to Date type occurs
     * @throws IOException exception thrown if I/O problems occurs
     * @throws java.text.ParseException another parse exception...
     */
    public static void main(String[] args) throws ParseException, IOException, java.text.ParseException {
        NoteBook noteBook = new NoteBook("notes.json");

        Options options = new Options();

        Option add = new Option("a", "add", true, "Notes you want to add");
        add.setArgs(2);
        add.setArgs(1);
        options.addOption(add);

        Option rm = new Option("r", "rm", true, "Notes you want to remove");
        rm.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(rm);

        Option show = new Option("s", "show", true, "Notes you want to show");
        show.setOptionalArg(true);
        show.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(show);

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        Option[] opts = commandLine.getOptions();
        for (Option op : opts) {
            switch (op.getLongOpt()) {
                case "add" -> {
                    if (args.length == 3) {
                        noteBook.add(args[1], args[2]);
                    } else if (args.length == 2) {
                        noteBook.add(args[1], null);
                    }
                }
                case "rm" -> {
                    if (args.length > 1) {
                        for (int i = 1; i < args.length; i++) {
                            noteBook.remove(args[i]);
                        }
                    }
                }
                case "show" -> {
                    if (args.length == 1) {
                        noteBook.show();
                    } else if (args.length > 2) {
                        List<String> substrings = List.of(Arrays.copyOfRange(args, 3, args.length));
                        noteBook.show(args[1], args[2], substrings);
                    }
                }
                default -> throw new IllegalArgumentException("Incorrect input");
            }
        }
    }
}
