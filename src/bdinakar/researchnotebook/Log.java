package bdinakar.researchnotebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

class Log {

    private HashSet<String> tags;
    private ArrayList<Event> logs;

    Log(String input) {
        tags = new HashSet<>();
        logs = new ArrayList<>();
        String[] split = input.trim().split("\\s+");
        tags.addAll(Arrays.asList(split));
    }

    private class Event {

        private Date eventDate;
        private String eventString;

        Event(String eventString) {
            this.eventString = eventString;
            this.eventDate = new Date();
        }

    }
}
