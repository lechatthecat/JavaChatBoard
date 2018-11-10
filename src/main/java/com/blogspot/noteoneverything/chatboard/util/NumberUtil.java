package com.blogspot.noteoneverything.chatboard.util;

public class NumberUtil {
    public static boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }  
}