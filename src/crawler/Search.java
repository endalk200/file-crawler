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

public class Search {

    private File root_directory;
    private String file_name;
    private int number_of_result = 0;
    private int recursive_index = 3;

    public Search(File root_directory, String file_name, int recursive_index) {
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

    public List<File> scan_filter_by_ext(String file_extension) throws  IOException {
        List<File> found_files = new ArrayList<File>();

        if (file_extension.startsWith(".")) {
            Stream<Path> traverse_tree = Files.walk(
                    Paths.get(this.get_root_directory().toString()), this.get_recursive_index());

            List<File> files = traverse_tree.filter(f -> f.endsWith(file_extension.toLowerCase()))
                                            .map(x -> x.toFile())
                                            .collect(Collectors.toList());

            for (File file : files) {
                if (file.toString().contains(this.get_file_name().toLowerCase())) {
                    found_files.add(file);
                    this.increment_result();
                }
            }
        }

        return found_files;
    }

    public List<File> scan() throws IOException, UncheckedIOException {
        List<File> found_files = new ArrayList<File>();

        Stream<Path> traverse_tree = Files.walk(
                Paths.get(this.get_root_directory().toString()));

//        List<File> files = traverse_tree.filter(path -> path.toString().contains(this.get_file_name().toLowerCase()))
//                                        .map(x -> x.toFile())
//                                        .collect(Collectors.toList());

        List<File> files = traverse_tree.filter(Files::isRegularFile)
                .map(x -> x.toFile())
                .collect(Collectors.toList());

        for (File file : files) {
            if (file.toString().toLowerCase().contains(this.get_file_name().toLowerCase())) {
                this.increment_result();
                found_files.add(file);
            }
        }

        return found_files;
    }

}