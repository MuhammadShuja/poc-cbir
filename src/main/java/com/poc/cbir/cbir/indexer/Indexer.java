package com.poc.cbir.cbir.indexer;

import java.io.IOException;
import java.util.List;

public abstract class Indexer {

    public abstract void index() throws IOException;

    public abstract void index(List<String> fileNames) throws IOException;
}
