package crawler;

import java.io.File;
import java.io.FilenameFilter;

public abstract class AbstractSearch implements FilenameFilter {
    String initials;

    public AbstractSearch(String initials) {
        this.initials = initials;
    }

    public AbstractSearch() {

    }

    public abstract boolean accept(File dir, String name);
}
