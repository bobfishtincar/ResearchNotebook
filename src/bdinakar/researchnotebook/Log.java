package bdinakar.researchnotebook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

class Log {

    private String title;
    private HashSet<String> initials;
    private HashSet<String> tags;
    private ArrayList<Event> logs;
    private Date logDate;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    Log(String input) {
        title = input;
        logDate = new Date();
        tags = new HashSet<>();
        logs = new ArrayList<>();
        initials = new HashSet<>();
    }

    void addInitials(String input) {
        String[] initialsArray = splitLine(input);
        for (String i: initialsArray) {
            initials.add(i);
        }
    }

    void startLogs(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            String[] split = splitLine(line);

            if (split.length != 0) {
                switch (split[0]) {

                    case ("tag"):
                        addTags(split);
                        break;

                    case ("qq"):
                        sc.close();
                        return;

                    default:
                        addLog(line);
                        break;
                }
            }
        }
    }

    void addTags(String[] input) {
        for (int i = 1; i < input.length; ++i) {
            addTags(input[i]);
        }
    }

    void addTags(String input) {
        String[] split = splitLine(input);
        for (String i: split) {
            tags.add(i.toLowerCase());
        }
    }

    private String[] splitLine(String input) {
        return input.trim().split("\\s+");
    }

    void addLog(String input) {
        logs.add(new Event(input));
    }

    public static void main(String[] args) {
        Log l = new Log("title");
        l.addTags("tag1 tag2 tag3");
        l.addLog("banana");
        l.addLog("apple");
        System.out.println(l);
    }

    @Override
    public String toString() {
        String rtn = "";
        rtn += (new SimpleDateFormat("MM/dd/yyyy HH:mm")).format(logDate);
        rtn += " ";
        rtn += title;
        rtn += "\n";
        rtn += "Initials: ";
        rtn += initials;
        rtn += "\n";
        rtn += "Tags: ";
        rtn += tags;
        rtn += "\n";
        for (Event e: logs) {
            rtn += e;
        }
        return rtn;
    }

    private class Event {

        private Date eventDate;
        private String eventString;

        Event(String eventString) {
            this.eventString = eventString;
            this.eventDate = new Date();
        }

        @Override
        public String toString() {
            return DATE_FORMAT.format(eventDate) + " " + eventString + "\n";
        }

    }
}
