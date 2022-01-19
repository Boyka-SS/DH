package com.jinke.driverhealth.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: fanlihao
 * @date: 2022/1/10
 */
public class EncryptUtil {

    /**
     * sha1加密方法
     * @param src
     * @return
     */
    public static String sha1(String src){
        try {
            char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
            MessageDigest md=MessageDigest.getInstance("sha1");
            byte[] digest=md.digest(src.getBytes());
            StringBuffer sb=new StringBuffer();
            for(byte b:digest){
                sb.append(chars[(b>>4)&15]);
                sb.append(chars[b&15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * sha1加密：参考链接：
 * https://blog.csdn.net/qq_34917117/article/details/106852513
 * https://www.cnblogs.com/king0207/p/14450717.html
 */

