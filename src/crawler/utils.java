package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public static String[] parse_drives() {
        File[] roots_list = File.listRoots();

        String[] drives = new String[roots_list.length];

        if (roots_list != null && roots_list.length > 0) {
            int index = 0;
            for (File drive : roots_list) {
                drives[index] = drive.toString();
                index++;
            }
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


    public static void write_logfile(String message) throws IOException {
        FileWriter fwriter = new FileWriter("log.log", true);
        PrintWriter log_file = new PrintWriter(fwriter);

        log_file.println(message);

        log_file.close();
    }

    public static void read_logfile() throws IOException {
        File log_file = new File("log.log");
        Scanner input_file = new Scanner(log_file);

        List<String> log = new ArrayList<String>();

        while (input_file.hasNext()) {
            log.add(input_file.nextLine());
        }

        input_file.close();
    }

}
