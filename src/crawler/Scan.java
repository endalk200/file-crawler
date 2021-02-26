/**
 * Scan
 * Main backend class that scans the drive for the specified file. Scans the disk
 * recursively with filename and file extension.
 */

package crawler;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scan {

    private File root_directory; // The root directory to scan
    private String file_name; // The target filename
    private int number_of_result = 0;
    private int recursive_index = 3; // recursive index. directory tree depth

    /**
     * Main API class to interface with backend logic.
     * @param root_directory : The root directory to scan
     * @param file_name
     */
    public Scan(File root_directory, String file_name) {
        this.set_root_directory(root_directory);
        this.set_file_name(file_name);
    }

    /**
     * Main API class to interface with backend logic.
     * @param root_directory : The root directory to scan
     * @param file_name
     * @param recursive_index : The maxDepth parameter is the maximum number of levels
     * of directories to visit.
     */
    public Scan(File root_directory, String file_name, int recursive_index) {
        this.set_file_name(file_name);
        this.set_root_directory(root_directory);
        this.set_recursive_index(recursive_index);
    }

    public void set_recursive_index(int recursive_index) { 
        this.recursive_index = recursive_index;
    }

    public int get_recursive_index() {
        return this.recursive_index;
    }

    public int get_number_of_result() {
        return this.number_of_result;
    }

    /**
     * increment_result
     * increment the number of files found matching the given specification.
     */
    public void increment_result() {
        this.number_of_result++;
    }

    public void set_root_directory(File root_directory) {
        this.root_directory = root_directory;
    }

    public  File get_root_directory() {
        return this.root_directory;
    }

    public void set_file_name(String file_name) {
        this.file_name = file_name;
    }

    public String get_file_name() {
        return this.file_name;
    }

    /**
     * scan_filter_by_ext:
     * method for filtering the file scan result using given file extension
     * @param file_extension
     * @return list of files that matches the given filter parameter
     * @throws IOException
     */
    public List<File> filter_by_ext(String file_extension) throws IOException {
        List<File> found_files = new ArrayList<File>();

        // finish this functionality

        return found_files;
    }

    public List<File> scan() throws IOException, UncheckedIOException {
        List<File> found_files = new ArrayList<File>();

        // create Files instance with the root directory
        Stream<Path> traverse_tree = Files.walk(
                Paths.get(this.get_root_directory().toString()), this.get_recursive_index());

        List<File> files = traverse_tree.filter(Files::isRegularFile).map(x -> x.toFile())
                                                                    .collect(Collectors.toList());

        traverse_tree.close();

        for (File file : files) {
            if (file.toString().toLowerCase().contains(this.get_file_name().toLowerCase())) {
                this.increment_result();
                found_files.add(file);
            }
        }

        return found_files;
    }

}