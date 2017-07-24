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

        System.out.println("You are now ready to start logging events!");
        System.out.println("Type \"qq\" to save the log and quit.");
        System.out.println("Type \"pic\" to add a picture to the log.");

        l.startLogs(sc);

        System.out.println(l);
    }

}
