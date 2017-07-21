package bdinakar.researchnotebook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

class Log {

    private String title;
    private HashSet<String> tags;
    private ArrayList<Event> logs;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    Log(String input) {
        title = input;
    }

    void addTags(String input) {
        tags = new HashSet<>();
        logs = new ArrayList<>();
        String[] split = input.trim().split("\\s+");
        for (String i: split) {
            tags.add(i.toLowerCase());
        }
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
        rtn += title;
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
            return DATE_FORMAT.format(eventDate) + ": " + eventString + "\n";
        }

    }
}
