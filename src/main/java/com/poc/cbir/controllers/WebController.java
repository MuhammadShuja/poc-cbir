package com.poc.cbir.controllers;

import com.poc.cbir.cbir.CBIR;
import com.poc.cbir.cbir.Utils;
import com.poc.cbir.models.Product;
import com.poc.cbir.utils.Logger;
import com.poc.cbir.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(){
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RedirectView search(@RequestParam("image") MultipartFile multipartFile,
                               @RequestParam(value = "min_similarity",
                                       required = false) Double minSimilarity,
                               @RequestParam(value = "max_products",
                                       required = false) Integer maxProducts) throws IOException {

        if(minSimilarity == null) minSimilarity = 0.5;
        if(maxProducts == null) maxProducts = 100;

        String fileName = Utils.getNewFileName()+".jpg";
        String uploadDir = CBIR.REQUESTS_DIR;

        UploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/results?"+
                "req="+fileName+
                "&min_similarity="+minSimilarity+
                "&max_products="+maxProducts, true);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public String results(Model model,
                          @RequestParam("req") String fileName,
                          @RequestParam(value = "min_similarity") double minSimilarity,
                          @RequestParam(value = "max_products") int maxProducts) throws IOException {

        Logger.log("Min similarity: " + minSimilarity);
        Logger.log("Max products: " + maxProducts);

        List<Product> products = CBIR.getInstance().search(
                new File(CBIR.REQUESTS_DIR+fileName),
                minSimilarity,
                maxProducts);

        Logger.log("Result products: " + products.size());

        model.addAttribute("products", products);
        model.addAttribute("name", fileName);

        return "results";
    }
}
