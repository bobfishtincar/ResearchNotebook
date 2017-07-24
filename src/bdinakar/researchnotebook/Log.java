package bdinakar.researchnotebook;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class Log {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private String title;
    private HashSet<String> initials;
    private ArrayList<Event> logs;
    private Date logDate;
    private BufferedImage i;
    private int count;

    Log(String input) {
        title = input;
        logDate = new Date();
        logs = new ArrayList<>();
        initials = new HashSet<>();
    }

    private void pic() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat m = new Mat();

        VideoCapture v = new VideoCapture();
        v.open(0);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton button = new JButton("Add Picture.");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                imageSet.put("image" + ++count + ".jpg", i);
                saveImage("image" + ++count + ".jpg", i);
                addLog("ADD IMAGE " + count + " HERE:\n\n\n");
            }
        } );

        frame.add(button);

        JLabel label = new JLabel();
        frame.add(label);
        frame.setVisible(true);

        while (frame.isVisible()) {
            v.read(m);
            i = bufferedImage(m);
            label.setIcon(new ImageIcon(i));
            label.repaint();
        }

    }

    private void saveImage(String fileName, BufferedImage image) {
        File outputfile = new File(toStringHeader(), fileName);
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage bufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        m.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData()); // get all the pixels
        return image;
    }

    void addInitials(String input) {
        String[] initialsArray = splitLine(input);
        for (String i : initialsArray) {
            initials.add(i);
        }
    }

    void startLogs(Scanner sc) {
        File f = new File(System.getProperty("User.dir"), toStringHeader());
        f.mkdir();
        while (true) {
            String line = sc.nextLine();
            String[] split = splitLine(line);

            if (split.length != 0) {
                switch (split[0]) {
                    case ("pic"):
                        pic();
                        break;
                    case ("qq"):
                        sc.close();
                        System.out.println(toString());
                        System.exit(0);
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
            File f = new File(System.getProperty("User.dir"), toStringHeader());
            f = new File(f, toStringHeader() + ".txt");
            writer = new BufferedWriter(new FileWriter(f));
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
        save();
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
        for (Event e : logs) {
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
