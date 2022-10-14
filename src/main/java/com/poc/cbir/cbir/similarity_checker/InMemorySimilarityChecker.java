package com.poc.cbir.cbir.similarity_checker;

import com.poc.cbir.cbir.indexer.InMemoryIndexer;
import com.poc.cbir.cbir.indexer.Indexer;
import com.poc.cbir.models.ExtractedProduct;
import com.poc.cbir.utils.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemorySimilarityChecker extends SimilarityChecker {

    public List<Map.Entry<String, Double>> check(Indexer indexer,
                                                 INDArray queryProduct) {
        Logger.log("Similarity check initiated");
        List<ExtractedProduct> products = ((InMemoryIndexer) indexer).getIndex();

        similarityMap = new HashMap<>();

        int productsCounter = 0;
        for (ExtractedProduct product : products) {
            double similarity = findSimilarity(queryProduct, product.getFeatures());

            if (productsCounter < MAX_SIMILAR_PRODUCTS) {
                if (similarity >= MIN_SIMILARITY_THRESHOLD) {
                    similarityMap.put(
                            product.getName(),
                            similarity);

                    productsCounter++;
                }
            } else {
                break;
            }
        }

        Logger.log("Similarity check completed");

        List<Map.Entry<String, Double>> sortedSimilarityMap = sortSimilarityMap();

        Logger.log("Similarity map sorted");

        return sortedSimilarityMap;
    }
}
