
package crawler;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Scan {

    private List<File> ROOT_DIRECTORIES;

    private int recursive_index;

    private String file_name;
    private String file_extension;
    private LocalDate file_created;

    public Scan(String file_name) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = 4;
        this.file_name = file_name;
    }

    public Scan(String file_name, int recursive_index) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = recursive_index;
        this.file_name = file_name;
    }

    public Scan(String file_name, String file_extension) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = 4;
        this.file_name = file_name;
        this.file_extension = file_extension;
    }

    public Scan(String file_name, String file_extension, int recursive_index) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = recursive_index;
        this.file_name = file_name;
        this.file_extension = file_extension;
    }

    public Scan(String file_name, LocalDate file_created) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = 4;
        this.file_name = file_name;
        this.file_created = file_created;
    }

    public Scan(String file_name, LocalDate file_created, int recursive_index) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = recursive_index;
        this.file_name = file_name;
        this.file_created = file_created;
    }

    public Scan(String file_name, String file_extension, LocalDate file_created) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = 4;
        this.file_name = file_name;
        this.file_extension = file_extension;
        this.file_created = file_created;
    }

    public Scan(String file_name, String file_extension, LocalDate file_created, int recursive_index) {
        this.ROOT_DIRECTORIES = Utils.get_root_directories();
        this.recursive_index = 4;
        this.file_name = file_name;
        this.file_extension = file_extension;
        this.file_created = file_created;
    }

    public List<File> scan() throws Exception {
        Utils.write_logfile(this.file_name);
        List<File> found_files = new ArrayList<File>();

        if (this.recursive_index == 4) {
            // Shalow scan with three recursive index
            // Check if there is file extension filter
            // Check if there is file created filter

            for (File root_directory : this.ROOT_DIRECTORIES) {
                Set<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                Files.walkFileTree(
                    Paths.get(root_directory.toURI()), 
                    fileVisitOptions, this.recursive_index, new FileVisitorImplementation(
                        this.file_name, found_files)
                );
            }
        } else {
            for (File root_directory : this.ROOT_DIRECTORIES) {
                Set<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                Files.walkFileTree(
                    Paths.get(root_directory.toURI()), 
                    fileVisitOptions, this.recursive_index, new FileVisitorImplementation(
                        this.file_name, found_files)
                );
            }
        }
        return found_files;
    }

    private void filter(String file_extension) {

    }

    private void filter(LocalDate file_created) {

    }

    private void filter(String file_extension, LocalDate file_created) {

    }

    public static void main(String[] args) {
        try {
            Scan scanner = new Scan("READ");
            System.out.println("Found " + scanner.scan().size());
            // for (File file : scanner.scan()) {
            //     System.out.println(file);
            // }
        } catch(Exception error_message) {
            System.out.println(error_message);
        }
    }
}


class FileVisitorImplementation implements FileVisitor<Path> {

    private List<File> files;
    private String file_name;

    public FileVisitorImplementation(String file_name, List<File> files) {
        this.file_name = file_name;
        this.files = files;
    }

    /**
     * This method is invoked before a directory’s entries are visited i.e., 
     * this method is called for each directory in the tree before its children 
     * (contents/entries of the directory) are visited.
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /**
     * postVisitDirectory: Called after all the directory entries are visited.
     * If there were any exceptions encountered during the traversal, the 
     * exception will be passed as an argument to this method. 
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /**
     * visitFile: Invoked for each file visited in the tree.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().toLowerCase().contains(this.file_name.toLowerCase())) {
            this.files.add(file.toFile());
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * visitFileFailed: Called when the file cannot be accessed. The exception
     * encountered is passed to the method.
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}