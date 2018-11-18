package com.blogspot.noteoneverything.chatboard.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }  
    public static String formatDate(Date d) {  
        if(d != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(d);
            return strDate;
        } else {
            return "";
        }
    } 
}