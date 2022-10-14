package com.poc.cbir.cbir.indexer;

import com.poc.cbir.cbir.CNNModel;
import com.poc.cbir.models.ExtractedProduct;
import com.poc.cbir.utils.Logger;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureExtractor {

    private static final List<ExtractedProduct> extractedProducts = new ArrayList<>();

    public static INDArray extractSingle(File file, boolean printLog) throws IOException {
        if(printLog)
        Logger.log("Single image feature extraction initiated for image " + file.getName());

        Map<String, INDArray> stringINDArrayMap = extract(file, CNNModel.vgg16());

        //Extract the features from the last fully connected layers
        INDArray fc2 = stringINDArrayMap.get("fc2");
        INDArray features = fc2.div(fc2.norm2Number());

        fc2.close();

        if(printLog)
        Logger.log("Single image feature extraction completed");

        return features;
    }

    public static List<ExtractedProduct> extractMultiple(File[] imageFiles) throws IOException {
        Logger.log("Multiple images feature extraction initiated");
        extractedProducts.clear();

        if (imageFiles == null) {
            Logger.log("Multiple images feature extraction stopped: NO FILES FOUND");
        } else {
            int total = imageFiles.length;
            int current = 0;
            for (File file : imageFiles) {
                current++;
                Logger.log("Extracting features for image " + current + " of " + total);

                Map<String, INDArray> stringINDArrayMap = extract(file, CNNModel.vgg16());

                //Extract the features from the last fully connected layers
                INDArray fc2 = stringINDArrayMap.get("fc2");
                INDArray features = fc2.div(fc2.norm2Number());

                extractedProducts.add(new ExtractedProduct(file.getName(), features));
            }

            Logger.log("Multiple images feature extraction completed");
        }

        return extractedProducts;
    }

    private static Map<String, INDArray> extract(File imageFile, ComputationGraph vgg16) throws IOException {
        // Convert file to INDArray
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(imageFile);

        // Mean subtraction pre-processing step for VGG
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);

        //Call the feedForward method to get a map of activations for each layer
        return vgg16.feedForward(image, false);
    }
}
