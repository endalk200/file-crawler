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

    public static String[] default_windows_dir = {"Desktop", "Documents", "Videos", "Downloads", "Pictures"};

    /**
     * parse_username parses the username of the user. The username is used to
     * prepare common directories for the given user such as
     * "C:/Users/username/Desktop"
     * @return string: username
     */
    public static String parse_username() {
        return System.getProperty("user.name");
    }

    /**
     * parse_drives parses the drives or partitions on the disk to crawle. This will
     * be used to prepare the directories to crawle.
     * 
     * @return string[]: array of drives
     */
    public static List<File> parse_drives() {
        File[] roots_list = File.listRoots();

        List<File> drives = new ArrayList<>();

        for (File drive : roots_list) {
            drives.add(drive);
        }

        return drives;
    }

    /**
     * default_windows_dir
     * returns collection of default windows directories in File instance
     */
    public static List<File> default_windows_dir() {
        String username = Utils.parse_username();

        List<File> default_dirs = new ArrayList<File>();

        for (String def_dir : default_windows_dir) {
            String dir = "C:\\Users\\" + username + "\\" + def_dir + "\\";
            default_dirs.add(new File(dir));
        }
        return default_dirs;
    }

    /**
     * is_directory return boolean value by evaluating wether the given File
     * instance is a directory or not. needed for validation purposes.
     * 
     * @param dir: the directory in question
     * @return boolean value wether it is directory or not
     */
    public static boolean is_directory(File dir) {
        return dir.isDirectory();
    }

    /**
     * is_directory return boolean value by evaluating wether the given File
     * instance represented by String input is a directory or not. needed for
     * validation purposes.
     * 
     * @param dir: the directory in question
     * @return boolean value wether it is directory or not
     */
    public static boolean is_directory(String dir) {
        File file = new File(dir);
        return file.isDirectory();
    }

    /**
     * is_file return boolean value by evaluating wether the given File instance is
     * a file or not. needed for validation purposes.
     * 
     * @param file: the directory in question
     * @return boolean value wether it is directory or not
     */
    public static boolean is_file(File file) {
        return file.isFile();
    }

    /**
     * is_file return boolean value by evaluating wether the given File instance
     * represented by String input is a file or not. needed for validation purposes.
     * 
     * @param dir: the directory in question
     * @return boolean value wether it is directory or not
     */
    public static boolean is_file(String __file) {
        File file = new File(__file);
        return file.isDirectory();
    }


    public static void write_logfile(String message) {
        try(
            FileWriter fwriter = new FileWriter("log.log", true);
            PrintWriter log_file = new PrintWriter(fwriter);
        ) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formated_now = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatted_date = now.format(formated_now);

            log_file.println(formatted_date + "$" + message);
        } catch(Exception error_message) {

        }
    }

    public static List<String[]> read_logfile() {
        List<String[]> processed_log = new ArrayList<>();
        try {
            File log_file = new File("log.log");
            Scanner input_file = new Scanner(log_file);

            List<String> log = new ArrayList<>();

            while (input_file.hasNext()) {
                log.add(input_file.nextLine());
            }
            input_file.close();

            for(String line : log) {
                String message = line.substring((line.indexOf("$") + 1), line.length());
                String timestamp = line.substring(0, (line.indexOf("$") - 1));

                String[] log_line = { timestamp, message };
                processed_log.add(log_line);
                // System.out.println(processed_log.get(0)[0]);
            }
        } catch(Exception error_message) {

        } finally {
            return processed_log;
        }
    }

    public static void main(String[] args) {
        write_logfile("hello");
        System.out.println(read_logfile().get(0)[1]);
    }

}
