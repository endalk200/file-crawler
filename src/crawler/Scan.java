
package crawler;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scan {

    private List<File> ROOT_DIRECTORIES = new ArrayList<>();

    private int recursive_index = 4;

    private String file_name;

    public Scan(String file_name) {
        this.recursive_index = 5;
        this.set_root_directories();
        this.file_name = file_name;
    }

    public void set_root_directories() {

        String[] default_windows_paths = { "Desktop", "Documents", "Videos", "Downloads", "Pictures" };

        for (String dir : default_windows_paths) {
            File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\" + dir + "\\");
            this.ROOT_DIRECTORIES.add(path);
        }

        for (File partition : File.listRoots()) {
            File C_DRIVE = new File("C:\\");
            if (C_DRIVE.equals(partition)) {
                continue;
            }
            this.ROOT_DIRECTORIES.add(partition);
        }
    }

    public List<File> scan() throws Exception {

        List<File> found_files = new ArrayList<>();

        for (File root_directory : this.ROOT_DIRECTORIES) {

            Set<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

            Files.walkFileTree(
                Paths.get(root_directory.toURI()), 
                fileVisitOptions, 
                this.recursive_index, 
                new FileVisitorImplementation(
                    this.file_name, found_files
                )
            );

        }
        return found_files;
    }

    public static void main(String[] args) {
        try {
            Scan scanner = new Scan("README");
            List<File> files = scanner.scan();
            System.out.println("Found " + files.size() + " files matching your query");;
        } catch (Exception error_message) {
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