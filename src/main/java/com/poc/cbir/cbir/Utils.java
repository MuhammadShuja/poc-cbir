package com.poc.cbir.cbir;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static int getIdFromName(String name){
        if (name.indexOf(".") > 0) {
            return Integer.parseInt(name.substring(0, name.lastIndexOf(".")));
        } else {
            return 0;
        }
    }

    public static String getNewFileName(){
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
}
