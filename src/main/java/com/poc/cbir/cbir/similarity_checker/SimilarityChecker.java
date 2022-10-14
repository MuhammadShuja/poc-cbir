package com.poc.cbir.cbir.similarity_checker;

import com.poc.cbir.cbir.indexer.Indexer;
import com.poc.cbir.utils.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SimilarityChecker {
    public double MIN_SIMILARITY_THRESHOLD = 0.5;
    public int MAX_SIMILAR_PRODUCTS = 100;

    protected Map<String, Double> similarityMap;

    public abstract List<Map.Entry<String, Double>>
    check(Indexer indexer, INDArray queryProduct) throws IOException;

    public double findSimilarity(INDArray array1, INDArray array2) {
        return Transforms.cosineSim(array1, array2);
    }

    public List<Map.Entry<String, Double>> sortSimilarityMap() {
        Logger.log("Ranking similar products");

        Set<Map.Entry<String, Double>> set = similarityMap.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<>(set);
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }
}
