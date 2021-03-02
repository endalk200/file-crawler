
# Code Samples

## By file extension

```java
    public static List<Path> findByFileExtension(Path path, String fileExtension)
            throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
        }
        return result;

    }
```

## By filename

```java
        public static List<Path> findByFileName(Path path, String fileName)
        throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<Path> result;
        // walk file tree, no more recursive loop
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isReadable)      // read permission
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName))
                    .collect(Collectors.toList());
        }
        return result;

    }
```

## By file size

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Files.walk example
public class FilesWalkExample6 {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:\\test\\");

        long fileSizeInBytes = 1024 * 1024 * 10; // 10MB

        List<Path> paths = findByFileSize(path, fileSizeInBytes);
        paths.forEach(x -> System.out.println(x));

    }

    // fileSize in bytes
    public static List<Path> findByFileSize(Path path, long fileSize)
        throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<Path> result;
        // walk file tree, no more recursive loop
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isReadable)              // read permission
                    .filter(p -> !Files.isDirectory(p))     // is a file
                    .filter(p -> checkFileSize(p, fileSize))
                    .collect(Collectors.toList());
        }
        return result;

    }

    private static boolean checkFileSize(Path path, long fileSize) {
        boolean result = false;
        try {
            if (Files.size(path) >= fileSize) {
                result = true;
            }
        } catch (IOException e) {
            System.err.println("Unable to get the file size of this file: " + path);
        }
        return result;
    }

}
```

## Update last modified date

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class FilesWalkExample7 {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:\\test\\");

        Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        setAllFilesModifiedDate(path, yesterday);

    }

    // set all files' last modified time to this instant
    public static void setAllFilesModifiedDate(Path path, Instant instant)
        throws IOException {

        try (Stream<Path> walk = Files.walk(path)) {
            walk
                    .filter(Files::isReadable)      // read permission
                    .filter(Files::isRegularFile)   // file only
                    .forEach(p -> {
                        try {
                            // set last modified time
                            Files.setLastModifiedTime(p, FileTime.from(instant));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

}
```

```java
public List<File> scan() throws IOException, UncheckedIOException, AccessDeniedException, NoSuchFileException {

        List<File> found_files = new ArrayList<File>();

        // create Files instance with the root directory
        Stream<Path> traverse_tree = Files.walk(Paths.get(this.get_root_directory().toString()),
                this.get_recursive_index());

        List<File> files = traverse_tree.filter(Files::isRegularFile).map(x -> x.toFile()).collect(Collectors.toList());

        traverse_tree.close();

        for (File file : files) {
            if (file.toString().toLowerCase().contains(this.get_file_name().toLowerCase())) {
                this.increment_result();
                found_files.add(file);
            }
        }

        return found_files;
    }
```





Map function from Stream maps one object to another