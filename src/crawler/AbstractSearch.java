package crawler;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Instances of classes that implement this interface are used to 
 * filter filenames. These instances are used to filter directory 
 * listings in the list method of class File, and by the Abstract 
 * Window Toolkit's file dialog component.
 */
public abstract class AbstractSearch implements FilenameFilter {
    String initials;

    /**
     * accept method from the FilenameFilter class
     * Tests if a specified file should be included in a file list.
     */
    public abstract boolean accept(File dir, String name);
}
