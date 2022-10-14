package com.poc.cbir.models;

import org.nd4j.linalg.api.ndarray.INDArray;

public class ExtractedProduct {
    private final String name;
    private final INDArray features;

    public ExtractedProduct(String name, INDArray features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public INDArray getFeatures() {
        return features;
    }
}
