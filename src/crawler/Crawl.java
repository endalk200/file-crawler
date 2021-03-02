
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

public class Crawl {

    public static void main(String[] args) throws Exception {
        List<File> found_files = new ArrayList<>();
        String filename = "README";

        List<File> ROOT_DIRECTORIES = Utils.get_root_directories();

        for (File root_directory : ROOT_DIRECTORIES) {
            Set<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

            Files.walkFileTree(
                    Paths.get(root_directory.toURI()),
                    fileVisitOptions,
                    4, new FileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if(file.toFile().toString().toLowerCase().contains(filename.toLowerCase())) {
                                found_files.add(file.toFile());
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    });
        }

//         Stream<File> filtered_files = found_files.stream().filter(file -> file.toString().contains("README"));
//           found_files.forEach(System.out::println);
        System.out.println(found_files.size());
//         List<File> found_fil = filtered_files.collect(Collectors.toList());
    }
}
