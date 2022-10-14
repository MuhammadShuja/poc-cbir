package com.poc.cbir.cbir.indexer;

import com.poc.cbir.cbir.CBIR;
import com.poc.cbir.cbir.DataProvider;
import com.poc.cbir.models.ExtractedProduct;
import com.poc.cbir.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InMemoryIndexer extends Indexer {

    private static List<ExtractedProduct> products;

    @Override
    public void index() throws IOException {
        File[] imageFiles = DataProvider.getFiles(CBIR.DATASET_DIR);
        products = FeatureExtractor.extractMultiple(imageFiles);
    }

    @Override
    public void index(List<String> fileNames) throws IOException {
        int total = fileNames.size();
        int current = 0;
        for (String fileName : fileNames) {
            current++;
            Logger.log("Extracting features for image " + current + " of " + total);

            File file = new File(CBIR.DATASET_DIR+fileName);

            products.add(new ExtractedProduct(fileName, FeatureExtractor.extractSingle(file, false)));
        }
    }

    public List<ExtractedProduct> getIndex() {
        return products;
    }
}
