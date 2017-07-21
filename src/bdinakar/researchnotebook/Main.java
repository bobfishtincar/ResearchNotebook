package bdinakar.researchnotebook;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Title: ");
        String title = sc.nextLine();

        Log l = new Log(title);

        System.out.println("Enter Initials: ");
        String initials = sc.nextLine();
        l.addInitials(initials);

        System.out.println("Enter Tags: ");
        String tags = sc.nextLine();
        l.addTags(tags);

        System.out.println("You are now ready to start logging events!");
        System.out.println("To add a tag, type the word \"tag\" before the tags.");
        System.out.println("Type \"qq\" to save the log and quit.");

        l.startLogs(sc);

        System.out.println(l);
    }

}
