package crawler;

import java.io.File;

public class Search extends AbstractSearch {

    @Override
    public boolean accept(File dir, String name) {
        return name.startsWith(initials);
    }
}
