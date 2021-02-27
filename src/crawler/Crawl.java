package crawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawl {

    private boolean deep_scan = false;
    private String file_name;
    private List<File> search_result = new ArrayList<>();

    public  Crawl(String file_name, boolean deep_scan) {
        if (deep_scan) {
            this.deep_scan = true;
        }
        this.set_file_name(file_name);
    }

    public void set_file_name(String file_name) {
        this.file_name = file_name;
    }

    public String get_file_name() {
        return this.file_name;
    }

    public void set_search_result(List<File> search_result) {
        this.search_result = search_result;
    }

    public List<File> get_search_result() {
        return this.search_result;
    }

    public List<File> shalow_scan() {

        List<File> search_result = new ArrayList<>();

        List<File> windows_DIR = Utils.default_windows_dir();
        List<File> drives = Utils.parse_drives();

        List<File> root_directory = new ArrayList<>();

        for (File dir : windows_DIR) { 
            if (dir.isDirectory()) {
                root_directory.add(dir);
            } else {
                continue;
            }
        }

        for(File dir : drives) { root_directory.add(dir); }

        for (File root_dir : root_directory) {
            Scan scanner = new Scan(root_dir, this.file_name);

            try {
                for (File file : scanner.scan()) {
                    search_result.add(file);
                }
            } catch (IOException error_message) {
                error_message.printStackTrace();
            }
        }

        this.set_search_result(search_result);
        return this.get_search_result();
    }

    public List<File> deep_scan() {
        List<File> search_result = new ArrayList<>();

        List<File> windows_DIR = Utils.default_windows_dir();
        List<File> drives = Utils.parse_drives();

        List<File> root_directory = new ArrayList<>();

        for (File dir : windows_DIR) { 
            if (dir.isDirectory()) {
                root_directory.add(dir);
            } else {
                continue;
            }
        }

        for(File dir : drives) { root_directory.add(dir); }

        for (File root_dir : root_directory) {
            Scan scanner = new Scan(root_dir, this.file_name, Integer.MAX_VALUE);

            try {
                for (File file : scanner.scan()) {
                    search_result.add(file);
                }
            } catch (IOException error_message) {
                error_message.printStackTrace();
            }
        }

        this.set_search_result(search_result);
        return this.get_search_result();
    }

    public List<File> scan() {
        List<File> result = new ArrayList<>();

        if (this.deep_scan) {
            result = this.deep_scan();
        } else {
            result = this.shalow_scan();
        }

        return result;
    }

    public static void main(String args[]) {
        Crawl scan_files = new Crawl("README", true);
        List<File> found_files = scan_files.scan();
        for (File file : found_files) {
            System.out.println(file);
        }
        System.out.println("\n\nFound " + found_files.size() + " files");
    }
}
