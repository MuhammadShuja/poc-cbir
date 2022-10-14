package com.poc.cbir.controllers;

import com.poc.cbir.cbir.CBIR;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/static")
public class StaticController {

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String fileName) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(CBIR.DATASET_DIR + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/requests/{filename}")
    public ResponseEntity<byte[]> getRequestImage(@PathVariable("filename") String fileName) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(CBIR.REQUESTS_DIR + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

}
