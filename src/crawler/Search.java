package crawler;

import java.io.File;

public class Search extends AbstractSearch {

    public Search(String initials) {
        this.initials = initials;
	}

	/**
     * accept method from the FilenameFilter class
     * Tests if a specified file should be included in a file list.
     */
    @Override
    public boolean accept(File dir, String name) {
        return name.startsWith(initials);
    }
}