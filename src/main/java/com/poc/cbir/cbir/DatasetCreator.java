package com.poc.cbir.cbir;

import com.poc.cbir.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatasetCreator {
    private static final int DOWNLOAD_STARTING_INDEX = 0;

    private static List<Product> productList;

    private static List<String> fileNames = new ArrayList<>();

    public static List<String> fromJson(String jsonFilePath){
        fileNames.clear();

        prepareProductList(jsonFilePath);
        downloadImages();

        return fileNames;
    }

    public static void prepareProductList(String jsonFilePath){
        Type type = new TypeToken<List<Product>>() {}.getType();
        try {
            productList = new Gson().fromJson(new FileReader(jsonFilePath), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Number of products: "+productList.size());
    }

    private static void downloadImages(){
        int total = productList.size();
        int skipped = 0;

        for(int i = DOWNLOAD_STARTING_INDEX; i < total; i++){
            Product p = productList.get(i);
            System.out.println("Downloading image "+(i+1)+" of "+total+" for ID: "+p.getId());

            URL url = null;
            try {
                url = new URL(p.getThumbnail());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if(url == null){
                skipped++;
                System.out.println(p.getId()+" skipped (Reason: invalid URL)");
                continue;
            }

            InputStream in = null;
            try {
                in = new BufferedInputStream(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(in == null){
                skipped++;
                System.out.println(p.getId()+" skipped (Reason: input stream null)");
                continue;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (true)
            {
                try {
                    if (!(-1!=(n=in.read(buf)))) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.write(buf, 0, n);
            }
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] response = out.toByteArray();

            try {
                FileOutputStream fos =
                        new FileOutputStream(CBIR.DATASET_DIR + p.getId()+".jpg");
                fileNames.add(p.getId()+".jpg");

                fos.write(response);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("========================= Download Summary ==========================");
        System.out.println("Start index: "+DOWNLOAD_STARTING_INDEX);
        System.out.println("Total images: "+total);
        System.out.println("Downloaded: "+(total - skipped));
        System.out.println("Skipped: "+skipped);
        System.out.println("=====================================================================");
    }

    private static void printProduct(Product product){
        System.out.println("======================== Product Information ========================");
        System.out.println("Product ID: "+product.getId());
        System.out.println("Product Thumbnail: "+product.getThumbnail());
        System.out.println("=====================================================================");
    }
}
