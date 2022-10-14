package com.poc.cbir.cbir.similarity_checker;

import com.poc.cbir.cbir.indexer.InDiskIndexer;
import com.poc.cbir.cbir.indexer.Indexer;
import com.poc.cbir.utils.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InDiskSimilarityChecker extends SimilarityChecker{

    public List<Map.Entry<String, Double>> check(Indexer indexer,
                                                 INDArray queryProduct) throws IOException {
        Logger.log("Similarity check initiated");

        List<File> products = ((InDiskIndexer) indexer).getIndex();

        similarityMap = new HashMap<>();

        int productsCounter = 0;
        for (File file : products) {
            INDArray features = Nd4j.readBinary(file);
            double similarity = findSimilarity(queryProduct, features);
            features.close();

            if(productsCounter < MAX_SIMILAR_PRODUCTS){
                if(similarity >= MIN_SIMILARITY_THRESHOLD){
                    similarityMap.put(
                            file.getName()+".jpg",
                            similarity);

                    productsCounter++;
                }
            }
            else{
                break;
            }
        }

        Logger.log("Similarity check completed");

        List<Map.Entry<String, Double>> sortedSimilarityMap = sortSimilarityMap();

        Logger.log("Similarity map sorted");

        return sortedSimilarityMap;
    }
}
