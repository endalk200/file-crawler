package crawler;

/**
 * AbstractSearch
 * This class is abstract class for the search functionality of the program.
 * this class searches for files in a given directory.
 * */

import java.io.FilenameFilter;

public abstract class AbstractSearch implements FilenameFilter {
    String initials;

    public AbstractSearch(String initials) {
        this.initials = initials;
    }

    public boolean accept(File dir, String name) {
        return name.startsWith(initials);
    }
}
