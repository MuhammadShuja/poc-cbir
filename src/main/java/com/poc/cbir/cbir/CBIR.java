package com.poc.cbir.cbir;

import com.poc.cbir.cbir.indexer.FeatureExtractor;
import com.poc.cbir.cbir.indexer.InDiskIndexer;
import com.poc.cbir.cbir.indexer.InMemoryIndexer;
import com.poc.cbir.cbir.indexer.Indexer;
import com.poc.cbir.cbir.similarity_checker.InDiskSimilarityChecker;
import com.poc.cbir.cbir.similarity_checker.InMemorySimilarityChecker;
import com.poc.cbir.cbir.similarity_checker.SimilarityChecker;
import com.poc.cbir.models.Product;
import com.poc.cbir.utils.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CBIR {
    public enum Config {IN_MEMORY, IN_DISK};
    public static Config CONFIG = Config.IN_DISK;

    public static final String ROOT_DIR = "/home/cbir/";
    public static final String FILES_DIR = ROOT_DIR+"files/";
    public static final String DATASET_DIR = ROOT_DIR+"dataset/";
    public static final String REQUESTS_DIR = ROOT_DIR+"requests/";
    public static final String FEATURES_DIR = ROOT_DIR+"features/";

    private static Indexer indexer;
    private static SimilarityChecker similarityChecker;

    private static volatile CBIR INSTANCE;

    public static CBIR getInstance() throws IOException {
        if (INSTANCE == null) {
            init();
        }
        return INSTANCE;
    }

    public static void init() throws IOException {
        CNNModel.vgg16();

        if (INSTANCE == null) {
            INSTANCE = new CBIR();

            switch (CBIR.CONFIG){
                case IN_DISK:
                    indexer = new InDiskIndexer();

                    similarityChecker = new InDiskSimilarityChecker();
                    break;
                case IN_MEMORY:
                    indexer = new InMemoryIndexer();

                    similarityChecker = new InMemorySimilarityChecker();
                    break;
            }
        }

        Logger.log("CBIR engine initialized");
    }

    public void index() throws IOException {
        long t0 = System.currentTimeMillis();

        Logger.log("CBIR indexing initiated...");

        indexer.index();

        long t1 = System.currentTimeMillis();
        double t = (double)(t1 - t0) / 1000.0;

        Logger.log("CBIR indexing completed in "+t+" seconds");
    }

    public List<Product> search(File file,
                                double minSimilarityThreshold,
                                int maxProductsToReturn) throws IOException {
        long t0 = System.currentTimeMillis();

        Logger.log("Searching similar products for image: "+file.getName()
                +" - location: "+file.getAbsolutePath());

        INDArray queryProduct = FeatureExtractor.extractSingle(file, true);

        if(minSimilarityThreshold > 0)
            similarityChecker.MIN_SIMILARITY_THRESHOLD = minSimilarityThreshold;
        if(maxProductsToReturn > 0)
            similarityChecker.MAX_SIMILAR_PRODUCTS = maxProductsToReturn;

        List<Map.Entry<String, Double>> similarProductsMap =
                similarityChecker.check(indexer, queryProduct);

        List<Product> similarProducts = new ArrayList<>();

        Logger.log("Creating product list");

        String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                +"/static/images/";

        for(Map.Entry<String, Double> entry : similarProductsMap){
            String productId = entry.getKey();

            similarProducts.add(new Product(
                    Utils.getIdFromName(productId),
                    baseUrl+productId
            ));
        }

        long t1 = System.currentTimeMillis();
        double t = (double)(t1 - t0) / 1000.0;

        Logger.log("Search completed in "+t+" seconds");

        return similarProducts;
    }

    public void upload(String jsonFileName) throws IOException {
        long t0 = System.currentTimeMillis();

        Logger.log("CBIR dataset upload initiated...");

        List<String> fileNames = DatasetCreator.fromJson(FILES_DIR+jsonFileName);

        long t1 = System.currentTimeMillis();
        double t = (double)(t1 - t0) / 1000.0;

        Logger.log("CBIR dataset upload completed in "+t+" seconds");

        // INDEX IMAGES
        indexer.index(fileNames);
    }
}
