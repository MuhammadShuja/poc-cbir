package com.poc.cbir.utils;

import com.poc.cbir.cbir.CBIR;
import org.slf4j.LoggerFactory;

public class Logger {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(CBIR.class);

    public static void log(String message){
        log.info(">>>>>>>>>> CBIR ("+CBIR.CONFIG+") : "+message);
    }
}
