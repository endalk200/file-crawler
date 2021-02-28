package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String log_file_directory = "C:\\Users\\" + 
                System.getProperty("user.name") + "\\file_crawler_log_file.csv";
    /**
     * get_root_directories Returns list of root_directories to start traversing
     * through.
     */
    public static List<File> get_root_directories() {

        List<File> ROOT_DIRECTORIES = new ArrayList<>();

        String[] default_c_directories_str = { "Desktop", "Documents", "Videos", "Downloads", "Pictures" };
        for (String dir : default_c_directories_str) {
            File directory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\" + dir);
            ROOT_DIRECTORIES.add(directory);
        }

        for (File drive : File.listRoots()) {
            File C_DRIVE = new File("C:\\");
            if (drive.equals(C_DRIVE)) {
                continue;
            }
            ROOT_DIRECTORIES.add(drive);
        }

        return ROOT_DIRECTORIES;
    }

    public static void write_logfile(String message) throws Exception {
        FileWriter fwriter = new FileWriter(log_file_directory, true);
        System.out.println(log_file_directory);
        PrintWriter log_file = new PrintWriter(fwriter);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formated_now = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formated_date = now.format(formated_now);

        log_file.println(formated_date + "$" + message);

        log_file.close();
    }

    public static List<String[]> read_logfile() throws Exception {
        List<String[]> processed_log = new ArrayList<>();

        File log_file = new File(log_file_directory);
        Scanner input_file = new Scanner(log_file);

        List<String> log = new ArrayList<>();

        while (input_file.hasNext()) {
            log.add(input_file.nextLine());
        }

        input_file.close();

        for (String line : log) {
            String timestamp = line.substring(0, (line.indexOf("$") - 1));
            String message = line.substring((line.indexOf("$") + 1), line.length());

            String[] log_line = { timestamp, message };
            processed_log.add(log_line);
            // System.out.println(processed_log.get(0)[0]);
        }

        return processed_log;
    }

    public static void main(String[] args) throws Exception {
        write_logfile("hello");
//        System.out.println(read_logfile().get(0)[0] + " <-> " + read_logfile().get(0)[1] );
    }

}
