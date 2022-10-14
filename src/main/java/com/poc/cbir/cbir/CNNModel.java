package com.poc.cbir.cbir;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.zoo.model.VGG16;

import java.io.IOException;

public class CNNModel {

    private static ComputationGraph model;

    public static ComputationGraph vgg16() throws IOException {
        if(model == null){
            ZooModel zooModel = VGG16.builder().build();
            model = (ComputationGraph) zooModel.initPretrained(PretrainedType.IMAGENET);
        }

        return model;
    }

}
