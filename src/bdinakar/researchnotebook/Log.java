package bdinakar.researchnotebook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

class Log {

    private String title;
    private HashSet<String> initials;
    private ArrayList<Event> logs;
    private Date logDate;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    Log(String input) {
        title = input;
        logDate = new Date();
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
                    case ("qq"):
                        sc.close();
                        save();
                        return;
                    default:
                        addLog(line);
                        break;
                }
            }
        }
    }

    private void save() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter( toStringHeader()));
            writer.write("\n" + toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        l.addLog("banana");
        l.addLog("apple");
        System.out.println(l);
    }

    public String toStringHeader() {
        String rtn = "";
        rtn += (new SimpleDateFormat("MM-dd-yyyy HH:mm")).format(logDate);
        rtn += " ";
        rtn += title;
        return rtn;
    }

    @Override
    public String toString() {
        String rtn = "";
        rtn += toStringHeader();
        rtn += "\n";
        rtn += "Initials: ";
        rtn += initials;
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
