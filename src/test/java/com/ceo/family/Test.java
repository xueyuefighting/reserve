package com.ceo.family;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private final static String emailFormat = "liuzhishanlmjsxm+%d@gmail.com";
    private final static String emailFormat_prefix = "liuzhishanlmjsxm+";

    private final static String emailFormat_regex = "liuzhishanlmjsxm(\\d+)@gmail.com";

    private static Pattern pattern = Pattern.compile(emailFormat_regex);
    public static void main(String[] args) {
        String em = "liuzhishanlmjsxm+2@gmail.com";
        em = em.replaceFirst("\\+","");
        String email = getEmail(em);
        System.out.println(email);
    }
    private static String getEmail(String email){
//        if(email==null || "".equals(email))
//            {return String.format(emailFormat, 1);}
//
//        Matcher matcher = pattern.matcher(email);
//
//        String s = "";
//        while(matcher.find()){
//            s = matcher.group(1);
//        }
//
//
//        return String.format(emailFormat, Integer.parseInt(s)+1);

//        return String.format(emailFormat, 1);
    }
}
