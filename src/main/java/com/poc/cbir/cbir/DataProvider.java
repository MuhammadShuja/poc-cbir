package com.poc.cbir.cbir;

import com.poc.cbir.utils.Logger;

import java.io.File;
import java.util.Objects;

public class DataProvider {

    public static File[] getFiles(String directoryPath) {
        Logger.log("Fetching files from directory");
        File folder = new File(directoryPath);
        if (folder.exists()) {
            Logger.log("Files found in directory: "
                    + Objects.requireNonNull(folder.listFiles()).length);
        }
        return folder.listFiles();
    }
}
