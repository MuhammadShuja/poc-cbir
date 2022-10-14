package com.poc.cbir;

import com.poc.cbir.cbir.CBIR;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;

@SpringBootApplication
@ServletComponentScan
public class CbirApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CbirApplication.class);
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CbirApplication.class, args);

        CBIR.CONFIG = CBIR.Config.IN_DISK;

        CBIR.getInstance().index();
    }
}
