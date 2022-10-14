package com.poc.cbir.cbir.indexer;

import com.poc.cbir.cbir.CBIR;
import com.poc.cbir.cbir.DataProvider;
import com.poc.cbir.cbir.Utils;
import com.poc.cbir.utils.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InDiskIndexer extends Indexer {

    @Override
    public void index() throws IOException {
        File[] imageFiles = DataProvider.getFiles(CBIR.DATASET_DIR);

        int total = imageFiles.length;
        int current = 0;
        for (File file : imageFiles) {
            current++;
            Logger.log("Extracting features for image " + current + " of " + total);

            String fileName = String.valueOf(Utils.getIdFromName(file.getName()));
            saveImageFeatures(fileName, FeatureExtractor.extractSingle(file, false));
        }
    }

    @Override
    public void index(List<String> fileNames) throws IOException {
        int total = fileNames.size();
        int current = 0;
        for (String fileName : fileNames) {
            current++;
            Logger.log("Extracting features for image " + current + " of " + total);

            File file = new File(CBIR.DATASET_DIR+fileName);
            saveImageFeatures(fileName, FeatureExtractor.extractSingle(file, false));
        }
    }

    private static void saveImageFeatures(String fileName, INDArray features) throws IOException {
        Nd4j.saveBinary(features, new File(CBIR.FEATURES_DIR + fileName));
        features.close();
    }

    public List<File> getIndex(){
        File[] files = DataProvider.getFiles(CBIR.FEATURES_DIR);

        List<File> fileList = Arrays.asList(files);
        Collections.shuffle(fileList);

        return fileList;
    }
}
