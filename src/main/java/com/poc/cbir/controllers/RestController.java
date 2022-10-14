package com.poc.cbir.controllers;

import com.poc.cbir.cbir.CBIR;
import com.poc.cbir.cbir.Utils;
import com.poc.cbir.models.Product;
import com.poc.cbir.utils.Logger;
import com.poc.cbir.utils.UploadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @RequestMapping(value = "/force-init", method = RequestMethod.GET)
    public void init() throws IOException {
        CBIR.init();
    }

    @RequestMapping(value = "/reindex", method = RequestMethod.GET)
    public void reindex() throws IOException {
        CBIR.getInstance().index();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("json") MultipartFile multipartFile) throws IOException {
        String fileName = Utils.getNewFileName()+".json";
        String uploadDir = CBIR.FILES_DIR;

        UploadUtil.saveFile(uploadDir, fileName, multipartFile);

        CBIR.getInstance().upload(fileName);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Product> search(@RequestParam("image") MultipartFile multipartFile,
                                @RequestParam(value = "min_similarity",
                                        required = false) Double minSimilarity,
                                @RequestParam(value = "max_products",
                                        required = false) Integer maxProducts) throws IOException {

        if(minSimilarity == null) minSimilarity = 0.5;
        if(maxProducts == null) maxProducts = 100;

        String fileName = Utils.getNewFileName()+".jpg";
        String uploadDir = CBIR.REQUESTS_DIR;

        UploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Logger.log("File to search: " + fileName);

        List<Product> products = CBIR.getInstance().search(
                new File(uploadDir+fileName),
                minSimilarity,
                maxProducts);

        Logger.log("Result products: " + products.size());

        return products;
    }
}
