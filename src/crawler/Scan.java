/**
 * Scan
 * Main backend class that scans the drive for the specified file. Scans the disk
 * recursively with filename and file extension.
 */

package crawler;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Scan {

    private File root_directory; // The root directory to scan
    private String file_name; // The target filename
    private int recursive_index = 3; // recursive index. directory tree depth

    /**
     * Main API class to interface with backend logic.
     * 
     * @param root_directory : The root directory to scan
     * @param file_name
     */
    public Scan(File root_directory, String file_name) {
        this.set_root_directory(root_directory);
        this.set_file_name(file_name);
    }

    /**
     * Main API class to interface with backend logic.
     * 
     * @param root_directory  : The root directory to scan
     * @param file_name
     * @param recursive_index : The maxDepth parameter is the maximum number of
     *                        levels of directories to visit.
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

    public void set_root_directory(File root_directory) {
        this.root_directory = root_directory;
    }

    public File get_root_directory() {
        return this.root_directory;
    }

    public void set_file_name(String file_name) {
        this.file_name = file_name;
    }

    public String get_file_name() {
        return this.file_name;
    }

    /**
     * scan_filter_by_ext: method for filtering the file scan result using given
     * file extension
     * 
     * @param file_extension
     * @return list of files that matches the given filter parameter
     * @throws IOException
     */
    public List<File> filter_by_ext(String file_extension) throws IOException {
        List<File> found_files = new ArrayList<File>();

        // finish this functionality

        return found_files;
    }

    public List<File> scan() throws IOException, UncheckedIOException, AccessDeniedException, NoSuchFileException {

        List<File> found_files = new ArrayList<File>();
        
        Set<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(
            Paths.get(this.get_root_directory().toURI()), 
            fileVisitOptions, this.get_recursive_index(), new FileVisitorImplementation(this.get_file_name(), found_files)
        );

        return found_files;
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